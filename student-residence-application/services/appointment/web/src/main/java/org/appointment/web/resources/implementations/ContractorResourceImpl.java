package org.appointment.web.resources.implementations;

import com.google.inject.Inject;
import org.appointment.common.Messages;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.service.services.interfaces.AppointmentService;
import org.appointment.web.Constants;
import org.appointment.web.helpers.HateoasResponseHelper;
import org.appointment.web.models.AppointmentListResponse;
import org.appointment.web.resources.interfaces.ContractorResource;

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
    private AppointmentService appointmentService;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Override
    @GET
    @RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_CARETAKER, Constants.ROLE_RESIDENT})
    @Path("{contractors-user-id}/appointments")
    public Response getAppointmentsOfContractor(@PathParam("contractors-user-id") String contractorsUserId) {
        String contextUserId = securityContext.getUserPrincipal().getName();
        boolean isAdminUser = securityContext.isUserInRole(Constants.ROLE_ADMIN);
        boolean isCaretakerUser = securityContext.isUserInRole(Constants.ROLE_CARETAKER);
        boolean isUserAuthorizedForThisResource = isAdminUser || isCaretakerUser || contractorsUserId.equalsIgnoreCase(contextUserId);

        if (!isUserAuthorizedForThisResource) {
            return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.USER_NOT_AUTHORISED_TO_OPERATE_RESOURCE);
        }

        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByContractor(contractorsUserId);

            AppointmentListResponse appointmentListResponse = new AppointmentListResponse(){
                {
                    setAppointments(appointments.stream()
                            .map(x -> HateoasResponseHelper.getAppointmentResponse(uriInfo.getBaseUri().toString(), x))
                            .collect(Collectors.toList()));
                }
            };

            return buildResponseObject(Response.Status.OK, appointmentListResponse);
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
