package org.contract.web.resources.implementations;

import com.google.inject.Inject;
import org.contract.common.Messages;
import org.contract.dataaccess.data.models.Contract;
import org.contract.service.services.interfaces.ContractService;
import org.contract.web.Constants;
import org.contract.web.helpers.HateoasResponseHelper;
import org.contract.web.models.ContractListResponse;
import org.contract.web.resources.interfaces.ContractorResource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path(Constants.RESOURCE_PATH_CONTRACTOR)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ContractorResourceImpl implements ContractorResource {
    @Inject
    private ContractService contractService;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Override
    @GET
    @RolesAllowed({Constants.ROLE_RESIDENT, Constants.ROLE_ADMIN})
    @Path("{contractors-user-id}/contracts")
    public Response getContractsOfContractor(@PathParam("contractors-user-id") String contractorsUserId) {
        String contextUserId = securityContext.getUserPrincipal().getName();
        boolean isAdminUser = securityContext.isUserInRole(Constants.ROLE_ADMIN);
        boolean isUserAuthorizedForThisResource = isAdminUser || contractorsUserId.equalsIgnoreCase(contextUserId);

        if (!isUserAuthorizedForThisResource) {
            return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.USER_NOT_AUTHORISED_TO_OPERATE_RESOURCE);
        }

        try {
            List<Contract> contracts = contractService.getContractsByContractor(contractorsUserId);

            ContractListResponse contractListResponse = new ContractListResponse(){
                {
                    setContracts(contracts.stream()
                            .map(x -> HateoasResponseHelper.getContractResponse(uriInfo.getBaseUri().toString(), x))
                            .collect(Collectors.toList()));
                }
            };

            return buildResponseObject(Response.Status.OK, contractListResponse);
        } catch (Exception ex) {
            return buildResponseObject(Response.Status.INTERNAL_SERVER_ERROR, Messages.INTERNAL_ERROR);
        }
    }

    private <T> Response buildResponseObject(Response.Status status, T entity) {
        return Response.status(status)
                .entity(entity)
                .build();
    }
}
