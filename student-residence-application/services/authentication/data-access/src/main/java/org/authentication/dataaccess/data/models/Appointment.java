package org.authentication.dataaccess.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.authentication.common.adapters.LocalDateAdapter;
import org.authentication.dataaccess.data.enums.AppointmentPriority;
import org.authentication.dataaccess.data.enums.AppointmentStatus;
import org.authentication.dataaccess.data.enums.AppointmentType;
//import org.appointment.service.adapters.AppointmentPriorityAdapter;
//import org.appointment.service.adapters.AppointmentStatusAdapter;
//import org.appointment.service.adapters.AppointmentTypeAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "appointment")
public class Appointment extends Entity implements Cloneable  {
    private String appointmentId;
    private String contractorsName;
    private String contractId;
    private String roomNumber;

    private AppointmentType appointmentType;
    private String issue;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate desiredDate;

    private AppointmentStatus status;

    private AppointmentPriority priority;

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
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
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
