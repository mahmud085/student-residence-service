package org.appointment.web.resources.implementations;

import com.google.inject.Inject;
import org.appointment.common.Messages;
import org.appointment.common.exceptions.*;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.models.PaginatedDataList;
import org.appointment.service.models.NewAppointment;
import org.appointment.service.services.interfaces.AppointmentService;
import org.appointment.web.Constants;
import org.appointment.web.helpers.PaginationMetadataHelper;
import org.appointment.web.models.*;
import org.appointment.web.resources.interfaces.AppointmentResource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path(Constants.RESOURCE_PATH_CONTRACT)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AppointmentResourceImpl implements AppointmentResource {
    @Inject
    private AppointmentService appointmentService;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Override
    @POST @RolesAllowed({Constants.ROLE_ADMINISTRATOR})
    public Response createAppointment(NewAppointment newAppointment) {
        String contextUserId = securityContext.getUserPrincipal().getName();

        if (newAppointment == null) {
            return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
        }

        try {
            Appointment createdAppointment = appointmentService.createAppointment(newAppointment, contextUserId);

            return buildResponseObject(Response.Status.CREATED, createdAppointment);
        } catch (ValidationException | InvalidOperationException ex) {
            return  buildResponseObject(Response.Status.BAD_REQUEST, ex.getMessage());
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
