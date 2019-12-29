package org.appointment.service.services.implementations;

import com.google.inject.Inject;
import org.appointment.common.exceptions.InvalidOperationException;
import org.appointment.common.exceptions.ObjectNotFoundException;
import org.appointment.common.exceptions.PaginationRangeOutOfBoundException;
import org.appointment.common.exceptions.ValidationException;
import org.appointment.common.helpers.DateHelper;
import org.appointment.common.helpers.ValidationHelper;
import org.appointment.common.Messages;
import org.appointment.dataaccess.data.enums.AppointmentStatus;
import org.appointment.dataaccess.data.enums.AppointmentType;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.models.PaginatedDataList;
import org.appointment.dataaccess.respositories.interfaces.AppointmentRepository;
import org.appointment.service.models.Contract;
import org.appointment.service.models.NewAppointment;
import java.time.LocalDate;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.UUID;


public class AppointmentServiceImpl implements org.appointment.service.services.interfaces.AppointmentService {
	@Inject
	private AppointmentRepository appointmentRepository;

	private Client client;

	@Override
	public Appointment createAppointment(NewAppointment newAppointment, String contextUserId) throws ValidationException, InvalidOperationException, ObjectNotFoundException {


		LocalDate createdOn = DateHelper.getCurrentDate();
		String contractId = newAppointment.getContractId();

		ValidationHelper<NewAppointment> validationHelper = new ValidationHelper<>();
		validationHelper.validate(newAppointment);


		try {
			Contract contract = getContractor(contractId);



			if (!contract.getRoomNumber().equals(newAppointment.getRoomNumber())) {

				throw new ValidationException(Messages.INVALID_ROOM_NUMBER);
			}

			if (newAppointment.getAppointmentType() == AppointmentType.MoveIn) {

				if (!contract.getContractStatus().equals("Confirmed")) {
					throw new InvalidOperationException(Messages.INVALID_CONTRACT);
				}

				LocalDate twoWeeksBefore = contract.getStartDate().minusWeeks(2);

				if (newAppointment.getDesiredDate().isBefore(twoWeeksBefore)) {

					throw new InvalidOperationException(Messages.INVALID_DESIRED_DATE);
				}
				if (newAppointment.getDesiredDate().isAfter(contract.getStartDate())) {

					throw new InvalidOperationException(Messages.INVALID_DESIRED_DATE);
				}

			}

			if (newAppointment.getAppointmentType() == AppointmentType.MoveOut) {

				LocalDate twoWeeksBefore = contract.getEndDate().minusWeeks(2);

				if (newAppointment.getDesiredDate().isBefore(twoWeeksBefore)) {
					throw new InvalidOperationException(Messages.INVALID_DESIRED_DATE);
				}
				if (newAppointment.getDesiredDate().isAfter(contract.getEndDate())) {
					throw new InvalidOperationException(Messages.INVALID_DESIRED_DATE);
				}
			}

			if(newAppointment.getDesiredDate().isBefore(LocalDate.now()))
				throw new InvalidOperationException(Messages.INVALID_DESIRED_DATE);

			Appointment appointment = new Appointment() {
				{
					setAppointmentId(UUID.randomUUID().toString());
					setContractorsName(newAppointment.getContractorsName().trim());
					setContractId(newAppointment.getContractId());
					setRoomNumber(newAppointment.getRoomNumber().trim());
					setAppointmentType(newAppointment.getAppointmentType());
					setIssue(newAppointment.getIssue());
					setPriority(newAppointment.getPriority());
					setDesiredDate(newAppointment.getDesiredDate());
					setStatus(AppointmentStatus.Received);
					setCreatedBy(contextUserId);
					setCreatedOn(createdOn);
				}
			};

			return appointmentRepository.add(appointment);
		} catch (Exception e){
			throw new ObjectNotFoundException(e.getMessage());
		}

	}

	@Override
	public Appointment gettAppointment(String appointmentId, String contextUserId) throws ObjectNotFoundException {
		Appointment appointment=appointmentRepository.getById(appointmentId);
		if(appointment==null)
			throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);

		return appointment;
	}

	@Override
	public List<Appointment> getallAppointment() throws ValidationException, InvalidOperationException, ObjectNotFoundException {
		List<Appointment> appointment=appointmentRepository.getAll();
		if(appointment==null)
			throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);

		return appointment;
	}

	@Override
	public PaginatedDataList<Appointment> getallAppointment(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
		return appointmentRepository.getAll(pageNum, pageSize);
	}

	@Override
	public List<Appointment> getallAppointment(LocalDate desiredDate) {
		return appointmentRepository.getAll(desiredDate);
	}

	@Override
	public PaginatedDataList<Appointment> getallAppointment(LocalDate desiredDate, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
		return appointmentRepository.getAll(desiredDate, pageNum, pageSize);
	}


	@Override
	public Appointment acceptAppointment(String appointmentId, String contextUserId) throws ObjectNotFoundException , InvalidOperationException {
		Appointment appointment = appointmentRepository.getById(appointmentId);
		LocalDate oneDayBefore = appointment.getDesiredDate().minusDays(1);

		if(appointment == null)
			throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);
		if(appointment.getStatus()== AppointmentStatus.Accepted)
			throw new InvalidOperationException(Messages.APPOINTMENT_ALREADY_ACCEPTED);
		if(!LocalDate.now().isBefore(appointment.getDesiredDate()))
			throw new InvalidOperationException(Messages.APPOINTMENT_DATE_EXPIRED);

		appointment = appointmentRepository.updateAppointmentStatus(appointmentId, AppointmentStatus.Accepted);
		if(appointment == null)
			throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);

		return appointment;
	}

	private Contract getContractor(String contractId) throws ObjectNotFoundException {
		client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:8081/api/contracts")
				.path("{contractId}")
				.resolveTemplate("contractId", contractId)
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();

		try {
			Contract contract = response.readEntity(Contract.class);
			return contract;
		} catch(Exception e) {

			throw new ObjectNotFoundException(Messages.CONTRACT_NOT_FOUND_WITH_ID);
		}
	}

}
