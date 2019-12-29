package org.authentication.web.models;

import org.authentication.dataaccess.data.models.Appointment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "response")
public class PaginatedAppointmentListResponse {
    @XmlElementWrapper(name="appointments")
    @XmlElement(name="appointment")
    private List<Appointment> appointments;
    private PaginationMetadata metadata;

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public PaginationMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(PaginationMetadata metadata) {
        this.metadata = metadata;
    }
}
