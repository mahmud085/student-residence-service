package org.appointment.web.resources.interfaces;

import org.appointment.service.models.NewAppointment;

import javax.ws.rs.core.Response;

public interface AppointmentResource {
    Response createAppointment(NewAppointment newAppointment);
    
    /**
     * 
     */
    
    Response getAppointment(String appointmentId);
    Response getAllAppointments();
    Response getAppointments(String desiredDate, int pageNum, int pageSize);
    Response acceptAppointment(String appointmentId);
    
}
