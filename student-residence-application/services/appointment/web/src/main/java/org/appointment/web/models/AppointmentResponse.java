package org.appointment.web.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.appointment.dataaccess.data.models.Appointment;

import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class AppointmentResponse extends Appointment {
    @XmlElementWrapper(name="links")
    @JacksonXmlProperty(localName = "link")
    private List<Hateoas> links;

    public List<Hateoas> getLinks() {
        return links;
    }

    public void setLinks(List<Hateoas> links) {
        this.links = links;
    }
}
