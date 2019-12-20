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

    
    /**
     * 
     * @author Kowshik Dipta Das Joy
     * 
     */
    Appointment gettAppointment(String appointmentId , String contextUserId) throws ValidationException, InvalidOperationException, ObjectNotFoundException;
    List<Appointment> allAppointment(String contextUserId) throws ValidationException, InvalidOperationException, ObjectNotFoundException;

    Appointment acceptAppointment(String appointmentId , String contextUserId) throws ValidationException, InvalidOperationException, ObjectNotFoundException; 
}
