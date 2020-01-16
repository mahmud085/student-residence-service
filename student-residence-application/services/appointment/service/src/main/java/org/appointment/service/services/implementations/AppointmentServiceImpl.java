package org.appointment.service.services.implementations;

import com.google.inject.Inject;
import org.appointment.common.Messages;
import org.appointment.common.exceptions.*;
import org.appointment.common.helpers.DateHelper;
import org.appointment.common.helpers.ValidationHelper;
import org.appointment.dataaccess.data.enums.AppointmentStatus;
import org.appointment.dataaccess.data.enums.AppointmentType;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.models.PaginatedDataList;
import org.appointment.dataaccess.respositories.interfaces.AppointmentRepository;
import org.appointment.service.models.Contract;
import org.appointment.service.models.NewAppointment;
import org.appointment.service.services.interfaces.AppointmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AppointmentServiceImpl implements AppointmentService {
	@Inject
	private AppointmentRepository appointmentRepository;

	@Override
	public Appointment createAppointment(NewAppointment newAppointment, Contract contract, String contextUserId) throws ValidationException, InvalidOperationException {
		LocalDate createdOn = DateHelper.getCurrentDate();

		ValidationHelper<NewAppointment> validationHelper = new ValidationHelper<>();
		validationHelper.validate(newAppointment);

		if (!contract.getRoomNumber().equals(newAppointment.getRoomNumber())) {

			throw new ValidationException(Messages.INVALID_ROOM_NUMBER);
		}

		Appointment oldAppointment = findExistingAppointment(contract.getContractorsUserId(), newAppointment);
		if(oldAppointment != null) {
			LocalDate today = LocalDate.now();
			if(oldAppointment.getStatus().equals(AppointmentStatus.Received) && oldAppointment.getDesiredDate().isAfter(today)) {
				throw new InvalidOperationException(Messages.APPOINTMENT_PENDING);
			}
		}

		if (newAppointment.getAppointmentType() == AppointmentType.MoveIn) {

			if (!contract.getStatus().equals("Confirmed")) {
				throw new InvalidOperationException(Messages.INVALID_CONTRACT);
			}

			LocalDate twoWeeksBefore = contract.getStartDate().minusWeeks(2);

			if (newAppointment.getDesiredDate().isBefore(twoWeeksBefore)) {

				throw new InvalidOperationException(Messages.INVALID_DESIRED_DATE_MOVEIN_OUT);
			}
			if (newAppointment.getDesiredDate().isAfter(contract.getStartDate())) {

				throw new InvalidOperationException(Messages.INVALID_DESIRED_DATE_MOVEIN_OUT);
			}

		}

		if (newAppointment.getAppointmentType() == AppointmentType.MoveOut) {

			LocalDate twoWeeksBefore = contract.getEndDate().minusWeeks(2);

			if (newAppointment.getDesiredDate().isBefore(twoWeeksBefore)) {
				throw new InvalidOperationException(Messages.INVALID_DESIRED_DATE_MOVEIN_OUT);
			}
			if (newAppointment.getDesiredDate().isAfter(contract.getEndDate())) {
				throw new InvalidOperationException(Messages.INVALID_DESIRED_DATE_MOVEIN_OUT);
			}
		}

		if(newAppointment.getDesiredDate().equals(LocalDate.now()) || newAppointment.getDesiredDate().isBefore(LocalDate.now()))
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
	}

	@Override
	public Appointment getAppointment(String appointmentId) throws ObjectNotFoundException {
		Appointment appointment=appointmentRepository.getById(appointmentId);
		if(appointment==null)
			throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);

		return appointment;
	}

	@Override
	public List<Appointment> getAllAppointments() throws ObjectNotFoundException {
		List<Appointment> appointment=appointmentRepository.getAll();
		if(appointment==null)
			throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);

		return appointment;
	}

	@Override
	public PaginatedDataList<Appointment> getAllAppointments(int pageNum, int pageSize) {
		return appointmentRepository.getAll(pageNum, pageSize);
	}

	@Override
	public List<Appointment> getAllAppointments(LocalDate desiredDate) {
		return appointmentRepository.getAll(desiredDate);
	}

	@Override
	public PaginatedDataList<Appointment> getAllAppointments(LocalDate desiredDate, int pageNum, int pageSize) {
		return appointmentRepository.getAll(desiredDate, pageNum, pageSize);
	}

	@Override
	public Appointment acceptAppointment(String appointmentId) throws InvalidOperationException, ObjectNotFoundException, OperationAlreadyExecutedException {
		Appointment appointment = appointmentRepository.getById(appointmentId);

		if (appointment == null) {
			throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);
		}

		if (appointment.getStatus()== AppointmentStatus.Accepted) {
			throw new OperationAlreadyExecutedException(Messages.APPOINTMENT_ALREADY_ACCEPTED);
		}

		if (!LocalDate.now().isBefore(appointment.getDesiredDate())) {
			throw new InvalidOperationException(Messages.APPOINTMENT_DATE_EXPIRED);
		}

		appointmentRepository.updateAppointmentStatus(appointmentId, AppointmentStatus.Accepted);

		return appointment;
	}

	@Override
	public Appointment denyAppointment(String appointmentId) throws InvalidOperationException, ObjectNotFoundException, OperationAlreadyExecutedException {
		Appointment appointment = appointmentRepository.getById(appointmentId);

		if (appointment == null) {
			throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);
		}

		if (appointment.getStatus()== AppointmentStatus.Denied) {
			throw new OperationAlreadyExecutedException(Messages.APPOINTMENT_ALREADY_DENIED);
		}

		if (!LocalDate.now().isBefore(appointment.getDesiredDate())) {
			throw new InvalidOperationException(Messages.APPOINTMENT_DATE_EXPIRED);
		}

		appointmentRepository.updateAppointmentStatus(appointmentId, AppointmentStatus.Denied);

		return appointment;
	}

	@Override
	public List<Appointment> getAppointmentsByContractor(String contractorsUserID) {
		return appointmentRepository.getAll(contractorsUserID);
	}

	private Appointment findExistingAppointment(String contractorsUserId, NewAppointment newAppointment) {
		List<Appointment> appointments = getAppointmentsByContractor(contractorsUserId);

		for(Appointment appointment: appointments) {
			if(appointment.getRoomNumber().equals(newAppointment.getRoomNumber()) && appointment.getAppointmentType().equals(newAppointment.getAppointmentType())) {
				return appointment;
			}
		}
		return null;
	}
}
