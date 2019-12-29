package org.appointment.web.helpers;

import org.appointment.dataaccess.data.models.Appointment;
import org.appointment.web.Constants;
import org.appointment.web.models.AppointmentResponse;
import org.appointment.web.models.Hateoas;

import java.util.ArrayList;

public class HateoasResponseHelper {
    public static AppointmentResponse getAppointmentResponse(String baseUri, Appointment appointment) {
        return new AppointmentResponse() {
            {
                setId(appointment.getId());
                setAppointmentId(appointment.getAppointmentId());
                setContractId(appointment.getContractId());
                setContractorsName(appointment.getContractorsName());
                setRoomNumber(appointment.getRoomNumber());
                setAppointmentType(appointment.getAppointmentType());
                setIssue(appointment.getIssue());
                setDesiredDate(appointment.getDesiredDate());
                setStatus(appointment.getStatus());
                setPriority(appointment.getPriority());
                setCreatedBy(appointment.getCreatedBy());
                setCreatedOn(appointment.getCreatedOn());
                setLinks(new ArrayList<Hateoas>(){
                    {
                        add(new Hateoas(){
                            {
                                setHref(String.format("%s%s/%s", baseUri, Constants.RESOURCE_PATH_APPOINTMENTS, appointment.getAppointmentId()));
                                setRel("self");
                            }
                        });
                    }
                });
            }
        };
    }
}
