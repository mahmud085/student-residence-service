package org.appointment.dataaccess.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.appointment.common.adapters.LocalDateAdapter;
import org.appointment.dataaccess.data.enums.AppointmentPriorityLevel;
import org.appointment.dataaccess.data.enums.AppointmentType;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "appointment")
public class Appointment extends Entity implements Cloneable  {
    private String appointmentId;
    private String contractorsName;
    private String roomNumber;
    private String issue;
    private AppointmentPriorityLevel priorityLevel;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;
    private AppointmentType appointmentType;

    @JsonIgnore
    private String createdBy;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate createdOn;

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }


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
    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public AppointmentPriorityLevel getAppointmentPriorityLevel() {
        return priorityLevel;
    }

    public void setAppointmentPriorityLevel(AppointmentPriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
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
