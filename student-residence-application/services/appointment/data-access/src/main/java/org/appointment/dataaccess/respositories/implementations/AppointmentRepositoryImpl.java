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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentRepositoryImpl implements AppointmentRepository {
	private static int id = 0;

	@Override
	public Appointment add(Appointment appointment) {
		appointment.setId(getNewId());

		DataStore.appointments.add(appointment);

		return appointment;
	}


	@Override
	public List<Appointment> getAll() {
		return new ArrayList<>(DataStore.appointments);
	}

	@Override
	public List<Appointment> getAll(LocalDate desiredDateFilter) {
		return DataStore.appointments
				.stream()
				.filter(x -> x.getDesiredDate().equals(desiredDateFilter))
				.collect(Collectors.toList());
	}

	@Override
	public PaginatedDataList<Appointment> getAll(LocalDate desiredDateFilter, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
		List<Appointment> filteredAppointmentList = DataStore.appointments
				.stream()
				.filter(x -> x.getDesiredDate().equals(desiredDateFilter))
				.collect(Collectors.toList());

		return getPaginatedAppointmentList(pageNum, pageSize, filteredAppointmentList);
	}

	@Override
	public Appointment getById(String appointmentId) {
		for(Appointment  appointment : DataStore.appointments){
			if(appointment.getAppointmentId().equals(appointmentId)) {
				return appointment;
			}

		}
		return null;
	}

	@Override
	public Appointment updateAppointmentStatus(String  appointmentId, AppointmentStatus status) {
		
		int appointmentIndex;
		for(Appointment  appointment : DataStore.appointments){
			if(appointment.getAppointmentId().equals(appointmentId)) {
				appointmentIndex= DataStore.appointments.indexOf(appointment);			
				appointment.setStatus(status);
				DataStore.appointments.set(appointmentIndex, appointment);
				return appointment;
			}
		
		}
		return null;
		
	}

	@Override
	public PaginatedDataList<Appointment> getAll(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException {
		List<Appointment> appointmentList = DataStore.appointments;

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
