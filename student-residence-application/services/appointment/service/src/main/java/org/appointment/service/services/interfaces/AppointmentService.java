package org.appointment.service.services.interfaces;

import org.appointment.common.exceptions.ObjectNotFoundException;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.common.exceptions.PaginationRangeOutOfBoundException;
import org.appointment.dataaccess.models.PaginatedDataList;
import org.appointment.common.exceptions.InvalidOperationException;
import org.appointment.common.exceptions.ValidationException;
import org.appointment.service.models.NewAppointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(NewAppointment newAppointment, String contextUserId) throws ValidationException, InvalidOperationException, ObjectNotFoundException;
    Appointment getAppointment(String appointmentId) throws ValidationException, InvalidOperationException, ObjectNotFoundException;
    List<Appointment> getAllAppointments() throws ValidationException, InvalidOperationException, ObjectNotFoundException;
    PaginatedDataList<Appointment> getAllAppointments(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
    List<Appointment> getAllAppointments(LocalDate desiredDate);
    PaginatedDataList<Appointment> getAllAppointments(LocalDate desiredDate, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
    Appointment acceptAppointment(String appointmentId) throws ValidationException, InvalidOperationException, ObjectNotFoundException;
    Appointment denyAppointment(String appointmentId) throws ValidationException, InvalidOperationException, ObjectNotFoundException;
    List<Appointment> getAppointmentsByContractor(String contractorsUserID);
}
