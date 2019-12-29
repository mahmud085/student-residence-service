package org.authentication.dataaccess.respositories.interfaces;

import org.authentication.common.exceptions.PaginationRangeOutOfBoundException;
import org.authentication.dataaccess.data.enums.AppointmentStatus;
import org.authentication.dataaccess.data.models.Appointment;
import org.authentication.dataaccess.models.PaginatedDataList;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends GenericRepository<Appointment> {
    PaginatedDataList<Appointment> getAll(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
    List<Appointment> getAll();
    List<Appointment> getAll(LocalDate desiredDateFilter);
    PaginatedDataList<Appointment> getAll(LocalDate desiredDateFilter, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
    Appointment getById(String appointmentId);
    Appointment updateAppointmentStatus(String appointmentId, AppointmentStatus status);
}
