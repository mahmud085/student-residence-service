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

import java.io.Console;
import java.time.LocalDate;
import java.util.List;

@Path(Constants.RESOURCE_PATH_APPOINTMENTS)
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
	@POST @RolesAllowed({Constants.ROLE_Resident})
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



	@GET @RolesAllowed({Constants.ROLE_Resident, Constants.ROLE_Caretaker})
	@Path("{appointment-id}")
	public Response getAppointment(@PathParam("appointment-id") String appointmentId) {

		String contextUserId = securityContext.getUserPrincipal().getName();
		if(appointmentId==null || appointmentId.length()==0) {
			return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
		}

		try {

			Appointment appointment=appointmentService.gettAppointment(appointmentId, contextUserId);

			return buildResponseObject(Response.Status.OK, appointment);
		} catch (ValidationException | InvalidOperationException | ObjectNotFoundException e) {
			// TODO Auto-generated catch block
			return  buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
		}

	}

	@Override
	@GET @RolesAllowed({Constants.ROLE_Resident, Constants.ROLE_Caretaker})
	public Response getAppointments(@QueryParam("desiredDate") String desiredDate
			, @QueryParam("pageNum") int pageNum
			, @QueryParam("pageSize") int pageSize) {
		boolean isdesiredDateFilterPresent = isValuePresent(desiredDate);
		LocalDate desiredDateConverted = isdesiredDateFilterPresent ? LocalDate.parse(desiredDate) : null;

		boolean isPaginationRequested = isPaginationRequested(pageNum, pageSize);
		String endpointPath = String.format("%s%s/%s", uriInfo.getBaseUri(), Constants.RESOURCE_PATH_APPOINTMENTS ,Constants.RESOURCE_PATH_GET_APPOINTMENTS);

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
			List<Appointment> AppointmentList;
			PaginationMetadata paginationMetadata;

			if (isPaginationRequested) {
				PaginatedDataList<Appointment> paginatedAppointmentList;
				if (isdesiredDateFilterPresent) {
					paginatedAppointmentList = appointmentService.getallAppointment(desiredDateConverted, pageNum, pageSize);
				} else {
					paginatedAppointmentList = appointmentService.getallAppointment(pageNum, pageSize);
				}

				AppointmentList = paginatedAppointmentList.getData();
				paginationMetadata = (new PaginationMetadataHelper(isPaginationRequested, endpointPath, pageNum, pageSize, paginatedAppointmentList.getTotalDataCount())
						.buildPaginationMetadata());
			} else if (isdesiredDateFilterPresent) {
				AppointmentList = appointmentService.getallAppointment(desiredDateConverted);
				paginationMetadata = new PaginationMetadata();
			} else {
				AppointmentList = appointmentService.getallAppointment();
				paginationMetadata = new PaginationMetadata();
			}

			PaginatedAppointmentListResponse paginatedAppointmentListResponse = new PaginatedAppointmentListResponse() {
				{
					setAppointments(AppointmentList);
					setMetadata(paginationMetadata);
				}
			};

			return buildResponseObject(Response.Status.OK, paginatedAppointmentListResponse);
		} catch (PaginationRangeOutOfBoundException ex) {
			return buildResponseObject(Response.Status.NO_CONTENT, null);
		} catch (Exception ex) {
			return buildResponseObject(Response.Status.INTERNAL_SERVER_ERROR, Messages.INTERNAL_ERROR);
		}
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



	@Override
	@PUT @RolesAllowed({Constants.ROLE_Caretaker})
	@Path("{appointment-id}")
	public Response acceptAppointment(@PathParam("appointment-id") String appointmentId) {
		String contextUserId = securityContext.getUserPrincipal().getName();
		if(appointmentId==null || appointmentId.length()==0) {
			return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
		}

		try {
			Appointment appointment = appointmentService.acceptAppointment(appointmentId, contextUserId);
			System.out.println(appointmentId);
			return buildResponseObject(Response.Status.OK, appointment.getStatus());
		} catch (ValidationException | InvalidOperationException | ObjectNotFoundException e) {
			// TODO Auto-generated catch block
			return  buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
		}
	}
}
