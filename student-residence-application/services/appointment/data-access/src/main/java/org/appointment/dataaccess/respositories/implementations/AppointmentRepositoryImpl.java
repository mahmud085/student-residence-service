package org.appointment.dataaccess.respositories.implementations;

import org.appointment.common.Messages;
import org.appointment.common.exceptions.ObjectNotFoundException;
import org.appointment.dataaccess.data.enums.AppointmentStatus;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.helpers.Configuration;
import org.appointment.dataaccess.models.PaginatedDataList;
import org.appointment.dataaccess.respositories.interfaces.AppointmentRepository;
import org.dataccess.Storage;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentRepositoryImpl implements AppointmentRepository {
	@Override
	public Appointment add(Appointment appointment) {
		Storage storage = Storage.instance().build(org.appointment.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));

		Appointment newAppointment = new Appointment();
		newAppointment.setAppointmentId(appointment.getAppointmentId());
		newAppointment.setAppointmentType(appointment.getAppointmentType());
		newAppointment.setContractId(appointment.getContractId());
		newAppointment.setContractorsName(appointment.getContractorsName());
		newAppointment.setCreatedBy(appointment.getCreatedBy());
		newAppointment.setCreatedOn(appointment.getCreatedOn());
		newAppointment.setDesiredDate(appointment.getDesiredDate());
		newAppointment.setId(appointment.getId());
		newAppointment.setIssue(appointment.getIssue());
		newAppointment.setPriority(appointment.getPriority());
		newAppointment.setRoomNumber(appointment.getRoomNumber());
		newAppointment.setStatus(appointment.getStatus());
		
		storage.insert(newAppointment);

		return appointment;
	}

	@Override
	public Appointment getById(String appointmentId) throws ObjectNotFoundException {
		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));

		Map<String, Object> paramList = new HashMap<>();
		paramList.put("appointmentId", appointmentId);

		try {
			return storage.executeForSingle("appointment.getAppointmentById", paramList);
		} catch (NoResultException ex) {
			throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);
		}
	}

	@Override
	public List<Appointment> getAll() {
		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));

		return storage.executeForMultiple("appointment.findAll", null);
	}

	@Override
	public List<Appointment> getAll(String createdByUserId) {
		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));

		Map<String, Object> paramList = new HashMap<>();
		paramList.put("createdByUserId", createdByUserId);

		return storage.executeForMultiple("appointment.findAllByCreatedBy", paramList);
	}

	@Override
	public List<Appointment> getAll(LocalDate desiredDateFilter) {
		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));

		Map<String, Object> paramList = new HashMap<>();
		paramList.put("desiredDate", desiredDateFilter);

		return storage.executeForMultiple("appointment.findAllByDesiredDate", paramList);
	}

	@Override
	public PaginatedDataList<Appointment> getAll(int pageNum, int pageSize) {
		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));

		return new PaginatedDataList() {
			{
				setData(storage.executeForMultiple("appointment.findAll", null, pageNum, pageSize));
				setTotalDataCount((int) (long) storage.executeForSingle("appointment.getCount", null));
			}
		};
	}

	@Override
	public PaginatedDataList<Appointment> getAll(LocalDate desiredDateFilter, int pageNum, int pageSize) {
		Storage storage = Storage.instance().build(Configuration.loadProperties("dbProperties.properties"));

		Map<String, Object> paramList = new HashMap<>();
		paramList.put("desiredDate", desiredDateFilter);

		return new PaginatedDataList() {
			{
				setData(storage.executeForMultiple("appointment.findAllByDesiredDate", paramList, pageNum, pageSize));
				setTotalDataCount((int) (long) storage.executeForSingle("appointment.getCountFilterByDesiredDate", paramList));
			}
		};
	}

	@Override
	public void updateAppointmentStatus(String  appointmentId, AppointmentStatus status) throws ObjectNotFoundException {
		Map<String, Object> paramList = new HashMap<>();
		paramList.put("appointmentId", appointmentId);
		paramList.put("status", status);

		Storage storage = Storage.instance().build(org.appointment.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));
		if(status.toString() == "Accepted") {
			int updateResult = storage.executeUpdate("appointment.acceptAppointment", paramList);
			if (updateResult == 0) {
				throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);
			}
		}
		else if(status.toString() == ("Denied")) {
			int updateResult = storage.executeUpdate("appointment.denyAppointment", paramList);
			if (updateResult == 0) {
				throw new ObjectNotFoundException(Messages.APPOINTMENT_NOT_FOUND);
			}
		}

	}
}
