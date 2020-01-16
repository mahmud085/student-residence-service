package org.appointment.dataaccess.respositories.interfaces;

import org.appointment.common.exceptions.ObjectNotFoundException;
import org.appointment.dataaccess.data.enums.AppointmentStatus;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.models.PaginatedDataList;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends GenericRepository<Appointment> {
    PaginatedDataList<Appointment> getAll(int pageNum, int pageSize);
    List<Appointment> getAll();
    List<Appointment> getAll(LocalDate desiredDateFilter);
    PaginatedDataList<Appointment> getAll(LocalDate desiredDateFilter, int pageNum, int pageSize);
    Appointment getById(String appointmentId) throws ObjectNotFoundException;
    void updateAppointmentStatus(String appointmentId, AppointmentStatus status) throws ObjectNotFoundException;
    List<Appointment> getAll(String createdByUserId);
}
