package org.appointment.dataaccess.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.appointment.common.adapters.LocalDateAdapter;
import org.appointment.dataaccess.data.enums.AppointmentPriority;
import org.appointment.dataaccess.data.enums.AppointmentStatus;
import org.appointment.dataaccess.data.enums.AppointmentType;
//import org.appointment.service.adapters.AppointmentPriorityAdapter;
//import org.appointment.service.adapters.AppointmentStatusAdapter;
//import org.appointment.service.adapters.AppointmentTypeAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "appointment")
public class Appointment extends Entity implements Cloneable  {
    private String contractorsName;
    private String roomNumber;
   // @XmlJavaTypeAdapter(AppointmentTypeAdapter.class)
    private AppointmentType appointmentType;
    private String issue;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate desiredDate;
    // @XmlJavaTypeAdapter(AppointmentStatusAdapter.class)
    private AppointmentStatus status;
    // @XmlJavaTypeAdapter(AppointmentPriorityAdapter.class)
    private AppointmentPriority priority;

    @JsonIgnore
    private String createdBy;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate createdOn;

    public String getContractorsName() {
        return contractorsName;
    }

    public void setContractorsName(String contractorsName) {
        this.contractorsName = contractorsName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public AppointmentPriority getPriority() {
        return priority;
    }

    public void setPriority(AppointmentPriority priority) {
        this.priority = priority;
    }
    public LocalDate getDesiredDate() {
        return desiredDate;
    }
    public void setDesiredDate(LocalDate desiredDate) {
        this.desiredDate = desiredDate;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Appointment clone() {
        try {
            return (Appointment) super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}
