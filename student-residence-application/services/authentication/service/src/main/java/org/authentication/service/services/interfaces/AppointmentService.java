package org.authentication.service.services.interfaces;

import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.dataaccess.data.models.Appointment;
import org.authentication.common.exceptions.PaginationRangeOutOfBoundException;
import org.authentication.dataaccess.models.PaginatedDataList;
import org.authentication.common.exceptions.InvalidOperationException;
import org.authentication.common.exceptions.ValidationException;
import org.authentication.service.models.NewAppointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(NewAppointment newAppointment, String contextUserId) throws ValidationException, InvalidOperationException, ObjectNotFoundException;
    Appointment gettAppointment(String appointmentId , String contextUserId) throws ValidationException, InvalidOperationException, ObjectNotFoundException;
    List<Appointment> getallAppointment() throws ValidationException, InvalidOperationException, ObjectNotFoundException;
    PaginatedDataList<Appointment> getallAppointment(int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
    List<Appointment> getallAppointment(LocalDate desiredDate);
    PaginatedDataList<Appointment> getallAppointment(LocalDate desiredDate, int pageNum, int pageSize) throws PaginationRangeOutOfBoundException;
    Appointment acceptAppointment(String appointmentId , String contextUserId) throws ValidationException, InvalidOperationException, ObjectNotFoundException;
}