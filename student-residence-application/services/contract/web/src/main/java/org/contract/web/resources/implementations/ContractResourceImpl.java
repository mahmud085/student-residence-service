package org.contract.web.resources.implementations;

import com.google.inject.Inject;
import javassist.tools.rmi.ObjectNotFoundException;
import org.contract.dataaccess.data.models.Contract;
import org.contract.common.exceptions.PaginationRangeOutOfBoundException;
import org.contract.dataaccess.models.PaginatedDataList;
import org.contract.common.exceptions.InvalidOperationException;
import org.contract.common.exceptions.ValidationException;
import org.contract.service.models.NewContract;
import org.contract.service.services.interfaces.ContractService;
import org.contract.web.Constants;
import org.contract.common.exceptions.PaginationAttributeException;
import org.contract.web.helpers.PaginationMetadataHelper;
import org.contract.web.models.*;
import org.contract.web.resources.interfaces.ContractResource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path(Constants.RESOURCE_PATH_CONTRACT)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ContractResourceImpl implements ContractResource {
    @Inject
    private ContractService contractService;

    @Context
    private UriInfo uriInfo;

    @Override
    @POST @RolesAllowed({Constants.ROLE_ADMINISTRATOR})
    public Response createContract(NewContract newContract) {
        try {
            Contract createdContract = contractService.createContract(newContract);

            return Response.status(Response.Status.CREATED)
                    .entity(createdContract)
                    .build();
        } catch (ValidationException ex) {
            return  Response.status(Response.Status.BAD_REQUEST)
                    .entity(buildErrorResponse(ex.getMessage()))
                    .build();
        } catch (Exception ex) {
            return  Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(buildErrorResponse("An internal error occurred."))
                    .build();
        }
    }

    @Override
    @GET @RolesAllowed({Constants.ROLE_ADMINISTRATOR, Constants.ROLE_Resident})
    @Path("{contract-id}")
    public Response getContract(@PathParam("contract-id") String contractId) {
        try {
            Contract contract = contractService.getContract(contractId);

            if (contract == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(buildErrorResponse("No contract found with the specified contract ID."))
                        .build();
            }

            return Response.status(Response.Status.OK)
                    .entity(contract)
                    .build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(buildErrorResponse("An internal error occurred."))
                    .build();
        }
    }

    @Override
    @GET @RolesAllowed({Constants.ROLE_ADMINISTRATOR, Constants.ROLE_Resident})
    public Response getContracts(@QueryParam("contractorsName") String contractorsName, @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) {
        boolean isContractorsNameFilterPresent = isValuePresent(contractorsName);
        boolean isPaginationRequested = isPaginationRequested(pageNum, pageSize);
        String endpointPath = String.format("%s%s", uriInfo.getBaseUri(), Constants.RESOURCE_PATH_CONTRACT);

        if (isPaginationRequested) {
            pageNum = pageNum == 0 ? Constants.DEFAULT_PAGE_NUM : pageNum;
            pageSize = pageSize == 0 ? Constants.DEFAULT_PAGE_SIZE : pageSize;

            try {
                validatePaginationAttributes(pageNum, pageSize);
            } catch (PaginationAttributeException ex) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(buildErrorResponse(ex.getMessage()))
                        .build();
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
                paginationMetadata = (new PaginationMetadataHelper(isPaginationRequested, endpointPath, pageNum, pageSize, paginatedContractList.getTotalDataCount())
                        .buildPaginationMetadata());
            } else if (isContractorsNameFilterPresent) {
                contractList = contractService.getContracts(contractorsName);
                paginationMetadata = new PaginationMetadata();
            } else {
                contractList = contractService.getContracts();
                paginationMetadata = new PaginationMetadata();
            }

            ContractListResponse contractListResponse = new ContractListResponse() {
                {
                    setContracts(contractList);
                    setMetadata(paginationMetadata);
                }
            };

            return Response.status(Response.Status.OK)
                    .entity(contractListResponse)
                    .build();
        } catch (PaginationRangeOutOfBoundException ex) {
            return Response.status(Response.Status.NO_CONTENT)
                    .entity(buildErrorResponse(ex.getMessage()))
                    .build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(buildErrorResponse("An internal error occurred."))
                    .build();
        }
    }

    @Override
    @PUT @RolesAllowed({Constants.ROLE_Resident})
    @Path("{contract-id}")
    public Response updateContract(@PathParam("contract-id") String contractId, ContractUpdateRequest contractUpdateRequest) {
        ContractUpdateOperation requestedOperation = contractUpdateRequest.getOperation();

        if (requestedOperation == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(buildErrorResponse("Invalid operation request."))
                    .build();
        }

        String successMsg = null;
        try {
            if (requestedOperation == ContractUpdateOperation.Confirm) {
                contractService.confirmContract(contractId);
                successMsg = "Contract successfully confirmed.";
            } else if (contractUpdateRequest.getOperation() == ContractUpdateOperation.Extend) {
                contractService.extendContract(contractId, contractUpdateRequest.getEndDate());
                successMsg = "Contract successfully extended.";
            } else if (contractUpdateRequest.getOperation() == ContractUpdateOperation.Terminate) {
                contractService.terminateContract(contractId, contractUpdateRequest.getEndDate());
                successMsg = "Contract successfully terminated.";
            }
        } catch (ObjectNotFoundException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(buildErrorResponse(ex.getMessage()))
                    .build();
        } catch (InvalidOperationException | ValidationException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(buildErrorResponse(ex.getMessage()))
                    .build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(buildErrorResponse("An internal error occurred."))
                    .build();
        }

        return Response.status(Response.Status.NO_CONTENT)
                .entity(buildSuccessResponse(successMsg))
                .build();
    }

    private boolean isPaginationRequested(int pageNum, int pageSize) {
        return isValuePresent(pageNum) && isValuePresent(pageSize);
    }

    private boolean isValuePresent(String value) {
        return (value != null && !value.isEmpty());
    }

    private boolean isValuePresent(int value) {
        return value > 0;
    }

    private void validatePaginationAttributes(int pageNum, int pageSize) throws PaginationAttributeException {
        if (pageNum < 1) {
            throw new PaginationAttributeException("Invalid 'pageNum' value.");
        }

        if (pageSize < 1) {
            throw new PaginationAttributeException("Invalid 'pageSize' value.");
        }

        if (pageSize > Constants.MAX_PAGE_SIZE) {
            throw new PaginationAttributeException("Max page size is " + Constants.MAX_PAGE_SIZE +".");
        }
    }

    private ErrorResponse buildErrorResponse(String errorMessage) {
        return new ErrorResponse() {
            {
                setErrorMessage(errorMessage);
            }
        };
    }

    private SuccessResponse buildSuccessResponse(String msg) {
        return new SuccessResponse() {
            {
                setMessage(msg);
            }
        };
    }
}
