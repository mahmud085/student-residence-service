package org.appointment.web.resources.implementations;

import com.google.inject.Inject;
import org.appointment.common.Messages;
import org.appointment.common.exceptions.*;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.models.PaginatedDataList;
import org.appointment.service.models.NewAppointment;
import org.appointment.service.services.interfaces.AppointmentService;
import org.appointment.web.Constants;
import org.appointment.web.helpers.HateoasResponseHelper;
import org.appointment.web.helpers.PaginationMetadataHelper;
import org.appointment.web.models.*;
import org.appointment.web.resources.interfaces.AppointmentResource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	@POST @RolesAllowed({Constants.ROLE_RESIDENT})
	public Response createAppointment(NewAppointment newAppointment) {
		String contextUserId = securityContext.getUserPrincipal().getName();

		if (newAppointment == null) {
			return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
		}

		try {
			Appointment createdAppointment = appointmentService.createAppointment(newAppointment, contextUserId);
			AppointmentResponse response = HateoasResponseHelper.getAppointmentResponse(uriInfo.getBaseUri().toString(), createdAppointment);

			return buildResponseObject(Response.Status.CREATED, response);
		} catch (ValidationException ex) {
			return  buildResponseObject(Response.Status.BAD_REQUEST, ex.getMessage());
		} catch (InvalidOperationException e) {
			return  buildResponseObject(Response.Status.PRECONDITION_FAILED, e.getMessage());
		} catch (Exception ex) {
			return buildResponseObject(Response.Status.INTERNAL_SERVER_ERROR, Messages.INTERNAL_ERROR);
		}
	}

	@GET @RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_RESIDENT, Constants.ROLE_CARETAKER})
	@Path("{appointment-id}")
	public Response getAppointment(@PathParam("appointment-id") String appointmentId) {
		String contextUserId = securityContext.getUserPrincipal().getName();
		boolean isAdminUser = securityContext.isUserInRole(Constants.ROLE_ADMIN);
		boolean isCaretakerUser = securityContext.isUserInRole(Constants.ROLE_CARETAKER);

		if(appointmentId == null || appointmentId.length() == 0) {
			return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.APPOINTMENT_ID_REQUIRED);
		}

		try {
			Appointment appointment = appointmentService.getAppointment(appointmentId);

			boolean isUserAuthorizedForThisResource = isAdminUser || isCaretakerUser || appointment.getCreatedBy().equalsIgnoreCase(contextUserId);

			if (!isUserAuthorizedForThisResource) {
				return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.USER_NOT_AUTHORISED_TO_OPERATE_RESOURCE);
			}

			AppointmentResponse response = HateoasResponseHelper.getAppointmentResponse(uriInfo.getBaseUri().toString(), appointment);

			return buildResponseObject(Response.Status.OK, response);
		} catch (ObjectNotFoundException e) {
			return  buildResponseObject(Response.Status.NOT_FOUND, e.getMessage());
		} catch (ValidationException e) {
			return  buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
		} catch (InvalidOperationException e) {
			return  buildResponseObject(Response.Status.PRECONDITION_FAILED, e.getMessage());
		}

	}

	@Override
	@GET @RolesAllowed({Constants.ROLE_ADMIN, Constants.ROLE_CARETAKER})
	public Response getAppointments(@QueryParam("desiredDate") String desiredDate
			, @QueryParam("pageNum") int pageNum
			, @QueryParam("pageSize") int pageSize) {
		boolean isDesiredDateFilterPresent = isValuePresent(desiredDate);

		if (isDesiredDateFilterPresent) {
			if (!isDateStringValid(desiredDate)) {
				return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.INVALID_DESIRED_DATE_STRING);
			}
		}

		LocalDate desiredDateConverted = isDesiredDateFilterPresent ? LocalDate.parse(desiredDate) : null;

		boolean isPaginationRequested = isPaginationRequested(pageNum, pageSize);
		Map<String, String> queryParams = isDesiredDateFilterPresent
				? new HashMap<String, String>() {{ put("desiredDate", desiredDate); }}
				: null;
		String endpointPath = String.format("%s%s/%s", uriInfo.getBaseUri(), Constants.RESOURCE_PATH_APPOINTMENTS, Constants.RESOURCE_PATH_GET_APPOINTMENTS);

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
			List<Appointment> appointmentList;
			PaginationMetadata paginationMetadata;

			if (isPaginationRequested) {
				PaginatedDataList<Appointment> paginatedAppointmentList;
				if (isDesiredDateFilterPresent) {
					paginatedAppointmentList = appointmentService.getAllAppointments(desiredDateConverted, pageNum, pageSize);
				} else {
					paginatedAppointmentList = appointmentService.getAllAppointments(pageNum, pageSize);
				}

				appointmentList = paginatedAppointmentList.getData();
				paginationMetadata = (new PaginationMetadataHelper(isPaginationRequested, endpointPath, pageNum, pageSize, paginatedAppointmentList.getTotalDataCount(), queryParams)
						.buildPaginationMetadata());
			} else if (isDesiredDateFilterPresent) {
				appointmentList = appointmentService.getAllAppointments(desiredDateConverted);
				paginationMetadata = new PaginationMetadata();
			} else {
				appointmentList = appointmentService.getAllAppointments();
				paginationMetadata = new PaginationMetadata();
			}

			PaginatedAppointmentListResponse paginatedAppointmentListResponse = new PaginatedAppointmentListResponse() {
				{
					setAppointments(appointmentList.stream()
							.map(x -> HateoasResponseHelper.getAppointmentResponse(uriInfo.getBaseUri().toString(), x))
							.collect(Collectors.toList()));
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

	@Override
	@PUT @RolesAllowed({Constants.ROLE_CARETAKER})
	@Path("{appointment-id}")
	public Response acceptAppointment(@PathParam("appointment-id") String appointmentId, AppointmentUpdateRequest appointmentUpdateRequest)
	{
		if(appointmentId == null || appointmentId.length() == 0) {
			return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.APPOINTMENT_ID_REQUIRED);
		}

		if (appointmentUpdateRequest == null) {
			return  buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
		}

		try {
			String successMsg = null;
			if (appointmentUpdateRequest.getOperation() == AppointmentUpdateOperation.Accept) {
				appointmentService.acceptAppointment(appointmentId);
				successMsg = Messages.SUCCESSFUL_ACCEPTANCE;
			}

			return buildResponseObject(Response.Status.OK, successMsg);
		} catch (ValidationException | ObjectNotFoundException e) {
			return  buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
		} catch (InvalidOperationException e) {
			return  buildResponseObject(Response.Status.PRECONDITION_FAILED, e.getMessage());
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

	private boolean isDateStringValid(String dateStr) {
		try {
			LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

	private <T> Response buildResponseObject(Response.Status status, T entity) {
		return Response.status(status)
				.entity(entity)
				.build();
	}
}
