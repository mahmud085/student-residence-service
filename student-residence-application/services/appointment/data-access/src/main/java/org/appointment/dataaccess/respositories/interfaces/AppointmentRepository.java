package org.appointment.dataaccess.respositories.interfaces;

import org.appointment.common.exceptions.PaginationRangeOutOfBoundException;
import org.appointment.dataaccess.data.enums.AppointmentStatus;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.models.PaginatedDataList;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends GenericRepository<Appointment> {
    PaginatedDataList<Appointment> getAll(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
    List<Appointment> getAll();
    Appointment getById(String appointmentId);
    Appointment updateAppointmentStatus(String appointmentId, AppointmentStatus status);
}
