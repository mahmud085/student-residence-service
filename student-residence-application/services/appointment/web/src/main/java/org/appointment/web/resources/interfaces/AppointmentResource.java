package org.appointment.web.resources.interfaces;

import org.appointment.service.models.NewAppointment;
import org.appointment.web.models.AppointmentUpdateRequest;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface AppointmentResource {
    Response createAppointment(NewAppointment newAppointment);
    
    /**
     * 
     */
    
    Response getAppointment(String appointmentId);
    Response getAppointments(String desiredDate, int pageNum, int pageSize);
    Response acceptAppointment(@PathParam("appointment-id") String appointmentId, AppointmentUpdateRequest appointmentUpdateRequest);
}
