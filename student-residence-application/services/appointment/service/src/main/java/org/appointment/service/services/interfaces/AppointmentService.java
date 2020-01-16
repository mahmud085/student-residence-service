package org.appointment.service.services.interfaces;

import org.appointment.common.exceptions.*;
import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.dataaccess.models.PaginatedDataList;
import org.appointment.service.models.Contract;
import org.appointment.service.models.NewAppointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(NewAppointment newAppointment, Contract contract, String contextUserId) throws ValidationException, InvalidOperationException;
    Appointment getAppointment(String appointmentId) throws ObjectNotFoundException;
    List<Appointment> getAllAppointments() throws ObjectNotFoundException;
    PaginatedDataList<Appointment> getAllAppointments(int pageNum, int pageSize);
    List<Appointment> getAllAppointments(LocalDate desiredDate);
    PaginatedDataList<Appointment> getAllAppointments(LocalDate desiredDate, int pageNum, int pageSize);
    Appointment acceptAppointment(String appointmentId) throws InvalidOperationException, ObjectNotFoundException, OperationAlreadyExecutedException;
    Appointment denyAppointment(String appointmentId) throws InvalidOperationException, ObjectNotFoundException, OperationAlreadyExecutedException;
    List<Appointment> getAppointmentsByContractor(String contractorsUserID);
}
