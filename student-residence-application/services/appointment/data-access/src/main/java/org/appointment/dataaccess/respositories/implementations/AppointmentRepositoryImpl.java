package org.appointment.dataaccess.respositories.implementations;

import org.appointment.common.Messages;
import org.appointment.common.exceptions.ObjectNotFoundException;
import org.appointment.common.exceptions.PaginationRangeOutOfBoundException;
import org.appointment.common.helpers.DateHelper;
import org.appointment.common.helpers.PaginationHelper;
import org.appointment.dataaccess.data.enums.AppointmentStatus;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.models.PaginatedDataList;
import org.appointment.dataaccess.respositories.interfaces.AppointmentRepository;
import org.appointment.dataaccess.store.DataStore;
import org.daaaccess.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.security.auth.login.Configuration;

public class AppointmentRepositoryImpl implements AppointmentRepository {




	private static int id = 0;


	
	@Override
	public Appointment add(Appointment appointment) {
		appointment.setId(getNewId());
		Storage storage = Storage.instance().build(org.appointment.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));

		Appointment a = new Appointment();
		a.setAppointmentId(appointment.getAppointmentId());
		a.setAppointmentType(appointment.getAppointmentType());
		a.setContractId(appointment.getContractId());
		a.setContractorsName(appointment.getContractorsName());
		a.setCreatedBy(appointment.getCreatedBy());
		a.setCreatedOn(appointment.getCreatedOn());
		a.setDesiredDate(appointment.getDesiredDate());
		a.setId(appointment.getId());
		a.setIssue(appointment.getIssue());
		a.setPriority(appointment.getPriority());
		a.setRoomNumber(appointment.getRoomNumber());
		a.setStatus(appointment.getStatus());
		
		storage.insert(a);
		DataStore.appointments.add(a);

		return appointment;
	}


	@Override
	public List<Appointment> getAll() {
		Storage storage = Storage.instance().build(org.appointment.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));
		return (List<Appointment>)storage.execute("appointment.findAll" , Appointment.class);
		
	}

	@Override
	public List<Appointment> getAll(LocalDate desiredDateFilter) {
		return getAll()
				.stream()
				.filter(x -> x.getDesiredDate().equals(desiredDateFilter))
				.collect(Collectors.toList());
	}

	@Override
	public PaginatedDataList<Appointment> getAll(LocalDate desiredDateFilter, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
		List<Appointment> filteredAppointmentList =getAll()
				.stream()
				.filter(x -> x.getDesiredDate().equals(desiredDateFilter))
				.collect(Collectors.toList());

		return getPaginatedAppointmentList(pageNum, pageSize, filteredAppointmentList);
	}

	@Override
	public Appointment getById(String appointmentId) {
		
		
		HashMap<String , String> hm = new HashMap<String, String>();
		hm.put("appointmentId", appointmentId);
		
		Storage storage = Storage.instance().build(org.appointment.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));
		List<Appointment> appointmentList = (List<Appointment>) storage.execute("appointment.getAppointmentById", hm);
		

		if(appointmentList.size()==0) {
			return null;
		}
		
		
		return appointmentList.get(0);
	}

	@Override
	public Appointment updateAppointmentStatus(String  appointmentId, AppointmentStatus status) {

		HashMap<String , String> hm = new HashMap<String, String>();
		hm.put("appointmentId", appointmentId);
		hm.put("status", status.name());
		
		Storage storage = Storage.instance().build(org.appointment.dataaccess.helpers.Configuration.loadProperties("dbProperties.properties"));
		if(storage.update("appointment.acceptAppointment", hm)==0)
			return null;
		
		return getById(appointmentId);
		

	}

	@Override
	public List<Appointment> getAll(String createdByUserId) {
		return getAll()
				.stream()
				.filter(x -> x.getCreatedBy().equalsIgnoreCase(createdByUserId))
				.collect(Collectors.toList());
	}

	@Override
	public PaginatedDataList<Appointment> getAll(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
		List<Appointment> appointmentList = getAll();

		return getPaginatedAppointmentList(pageNum, pageSize, appointmentList);
	}


	private synchronized int getNewId() {
		return ++id;
	}


	private PaginatedDataList<Appointment> getPaginatedAppointmentList(int pageNum, int pageSize, List<Appointment> appointmentList) throws PaginationRangeOutOfBoundException {
		PaginationHelper paginationHelper = new PaginationHelper(pageNum, pageSize, appointmentList.size());

		if (paginationHelper.isIndexOutOfRange()) {
			throw new PaginationRangeOutOfBoundException(Messages.PAGINATION_RANGE_EXCEEDS);
		}

		int startIndex = paginationHelper.getStartIndex();
		int endIndex = paginationHelper.getEndIndex();

		return new PaginatedDataList<Appointment>() {
			{
				setData(appointmentList.subList(startIndex, endIndex+1));
				setTotalDataCount(appointmentList.size());
			}
		};
	}

}
