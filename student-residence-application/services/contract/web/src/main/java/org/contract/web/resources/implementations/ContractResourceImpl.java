package org.contract.web.resources.implementations;

import com.google.inject.Inject;
import org.contract.common.Messages;
import org.contract.common.exceptions.*;
import org.contract.dataaccess.data.models.Contract;
import org.contract.dataaccess.models.PaginatedDataList;
import org.contract.service.models.NewContract;
import org.contract.service.models.UserRole;
import org.contract.service.services.interfaces.ContractService;
import org.contract.web.Constants;
import org.contract.web.helpers.HateoasResponseHelper;
import org.contract.web.helpers.HttpRequestHelper;
import org.contract.web.helpers.PaginationMetadataHelper;
import org.contract.web.models.*;
import org.contract.web.resources.interfaces.ContractResource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path(Constants.RESOURCE_PATH_CONTRACT)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ContractResourceImpl implements ContractResource {
    @Inject
    private ContractService contractService;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Override
    @POST @RolesAllowed({Constants.ROLE_ADMIN})
    public Response createContract(NewContract newContract) {
        String contextUserId = securityContext.getUserPrincipal().getName();

        if (newContract == null) {
            return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
        }

        try {
            User contractorUser = HttpRequestHelper.getUser(newContract.getContractorsUserId(), securityContext.getAuthenticationScheme());
            if (contractorUser == null) {
                return buildResponseObject(Response.Status.BAD_REQUEST, Messages.INVALID_CONTRACTORS_USER_ID);
            }

            if (contractorUser.getUserType() != UserRole.Resident) {
                return buildResponseObject(Response.Status.BAD_REQUEST, Messages.INVALID_CONTRACTORS_USER_ROLE);
            }

            Contract createdContract = contractService.createContract(newContract, contextUserId);
            ContractResponse response = HateoasResponseHelper.getContractResponse(uriInfo.getBaseUri().toString(), createdContract);

            return buildResponseObject(Response.Status.CREATED, response);
        } catch (ValidationException ex) {
            return  buildResponseObject(Response.Status.BAD_REQUEST, ex.getMessage());
        } catch (InvalidOperationException ex) {
            return  buildResponseObject(Response.Status.PRECONDITION_FAILED, ex.getMessage());
        } catch (Exception ex) {
            return buildResponseObject(Response.Status.INTERNAL_SERVER_ERROR, Messages.INTERNAL_ERROR);
        }
    }

    @Override
    @GET @RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_RESIDENT})
    @Path("{contract-id}")
    public Response getContract(@PathParam("contract-id") String contractId) {
        String contextUserId = securityContext.getUserPrincipal().getName();
        boolean isAdminUser = securityContext.isUserInRole(Constants.ROLE_ADMIN);

        try {
            Contract contract = contractService.getContract(contractId);

            boolean isUserAuthorizedForThisResource = isAdminUser || contract.getContractorsUserId().equalsIgnoreCase(contextUserId);

            if (!isUserAuthorizedForThisResource) {
                return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.USER_NOT_AUTHORISED_TO_OPERATE_RESOURCE);
            }

            ContractResponse response = HateoasResponseHelper.getContractResponse(uriInfo.getBaseUri().toString(), contract);

            return buildResponseObject(Response.Status.OK, response);
        } catch (ObjectNotFoundException ex) {
            return buildResponseObject(Response.Status.NOT_FOUND, ex.getMessage());
        }
        catch (Exception ex) {
            return buildResponseObject(Response.Status.INTERNAL_SERVER_ERROR, Messages.INTERNAL_ERROR);
        }
    }

    @Override
    @GET @RolesAllowed({Constants.ROLE_ADMIN})
    public Response getContracts(@QueryParam("contractorsName") String contractorsName
            , @QueryParam("pageNum") int pageNum
            , @QueryParam("pageSize") int pageSize) {
        boolean isContractorsNameFilterPresent = isValuePresent(contractorsName);
        boolean isPaginationRequested = isPaginationRequested(pageNum, pageSize);
        Map<String, String> queryParams = isContractorsNameFilterPresent
                ? new HashMap<String, String>() {{ put("contractorsName", contractorsName); }}
                : null;
        String endpointPath = String.format("%s%s", uriInfo.getBaseUri(), Constants.RESOURCE_PATH_CONTRACT);

        if (isPaginationRequested) {
            pageNum = pageNum == 0 ? Constants.DEFAULT_PAGE_NUM : pageNum;
            pageSize = pageSize == 0 ? Constants.DEFAULT_PAGE_SIZE : pageSize;

            try {
                validatePaginationAttributes(pageNum, pageSize);
            } catch (PaginationAttributeException ex) {
                return buildResponseObject(Response.Status.BAD_REQUEST, ex.getMessage());
            }
        }

        try {
            List<Contract> contractList;
            PaginationMetadata paginationMetadata;

            if (isPaginationRequested) {
                PaginatedDataList<Contract> paginatedContractList;
                if (isContractorsNameFilterPresent) {
                    paginatedContractList = contractService.getContracts(contractorsName, pageNum, pageSize);
                } else {
                    paginatedContractList = contractService.getContracts(pageNum, pageSize);
                }

                contractList = paginatedContractList.getData();
                paginationMetadata = (new PaginationMetadataHelper(isPaginationRequested, endpointPath, pageNum, pageSize, paginatedContractList.getTotalDataCount(), queryParams)
                        .buildPaginationMetadata());
            } else if (isContractorsNameFilterPresent) {
                contractList = contractService.getContracts(contractorsName);
                paginationMetadata = new PaginationMetadata();
            } else {
                contractList = contractService.getContracts();
                paginationMetadata = new PaginationMetadata();
            }

            PaginatedContractListResponse paginatedContractListResponse = new PaginatedContractListResponse() {
                {
                    setContracts(contractList.stream()
                            .map(x -> HateoasResponseHelper.getContractResponse(uriInfo.getBaseUri().toString(), x))
                            .collect(Collectors.toList()));
                    setMetadata(paginationMetadata);
                }
            };

            return buildResponseObject(Response.Status.OK, paginatedContractListResponse);
        } catch (Exception ex) {
            return buildResponseObject(Response.Status.INTERNAL_SERVER_ERROR, Messages.INTERNAL_ERROR);
        }
    }

    @Override
    @PATCH @RolesAllowed({Constants.ROLE_RESIDENT})
    @Path("{contract-id}")
    public Response updateContract(@PathParam("contract-id") String contractId, ContractUpdateRequest contractUpdateRequest) {
        String contextUserId = securityContext.getUserPrincipal().getName();

        if (contractId == null || contractId.isEmpty()) {
            return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.CONTRACT_ID_REQUIRED);
        }

        try {
            Contract contract = contractService.getContract(contractId);

            boolean isUserAuthorizedForThisResource = contract.getContractorsUserId().equalsIgnoreCase(contextUserId);

            if (!isUserAuthorizedForThisResource) {
                return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.USER_NOT_AUTHORISED_TO_OPERATE_RESOURCE);
            }
        } catch (ObjectNotFoundException ex) {
            return buildResponseObject(Response.Status.NOT_FOUND, ex.getMessage());
        }

        if (contractUpdateRequest == null) {
            return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
        }

        ContractUpdateOperation requestedOperation = contractUpdateRequest.getOperation();

        if (requestedOperation == null) {
            return buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUIRED_OPERATION);
        }

        String successMsg = null;
        try {
            if (requestedOperation == ContractUpdateOperation.Confirm) {
                contractService.confirmContract(contractId);
                successMsg = Messages.SUCCESSFUL_CONFIRMATION;
            } else {
                if (contractUpdateRequest.getEndDate() == null) {
                    return buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUIRED_END_DATE);
                }

                if (contractUpdateRequest.getOperation() == ContractUpdateOperation.Extend) {
                    contractService.extendContract(contractId, contractUpdateRequest.getEndDate());
                    successMsg = Messages.SUCCESSFUL_EXTENSION;
                } else if (contractUpdateRequest.getOperation() == ContractUpdateOperation.Terminate) {
                    contractService.terminateContract(contractId, contractUpdateRequest.getEndDate());
                    successMsg = Messages.SUCCESSFUL_TERMINATION;
                }
            }
        } catch (OperationAlreadyExecutedException ex) {
            return buildResponseObject(Response.Status.OK, ex.getMessage());
        } catch (ObjectNotFoundException ex) {
            return buildResponseObject(Response.Status.NOT_FOUND, ex.getMessage());
        } catch (ValidationException ex) {
            return buildResponseObject(Response.Status.BAD_REQUEST, ex.getMessage());
        } catch (InvalidOperationException ex) {
            return  buildResponseObject(Response.Status.PRECONDITION_FAILED, ex.getMessage());
        } catch (Exception ex) {
            return buildResponseObject(Response.Status.INTERNAL_SERVER_ERROR, Messages.INTERNAL_ERROR);
        }

        return buildResponseObject(Response.Status.OK, successMsg);
    }

    private boolean isPaginationRequested(int pageNum, int pageSize) {
        return isValuePresent(pageNum) || isValuePresent(pageSize);
    }

    private boolean isValuePresent(String value) {
        return (value != null && !value.isEmpty());
    }

    private boolean isValuePresent(int value) {
        return value > 0;
    }

    private void validatePaginationAttributes(int pageNum, int pageSize) throws PaginationAttributeException {
        if (pageNum < 1) {
            throw new PaginationAttributeException(Messages.INVALID_PAGE_NUM);
        }

        if (pageSize < 1) {
            throw new PaginationAttributeException(Messages.INVALID_PAGE_SIZE);
        }

        if (pageSize > Constants.MAX_PAGE_SIZE) {
            throw new PaginationAttributeException("Max page size is " + Constants.MAX_PAGE_SIZE +".");
        }
    }

    private <T> Response buildResponseObject(Response.Status status, T entity) {
        return Response.status(status)
                .entity(entity)
                .build();
    }
}
