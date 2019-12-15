package org.appointment.service.models;

import org.appointment.common.Constants;
import org.appointment.common.Messages;
import org.appointment.common.adapters.LocalDateAdapter;
import org.appointment.common.validation.annotations.HasValue;
import org.appointment.dataaccess.data.enums.ContractStatus;
import org.appointment.service.adapters.AppointmentTypeAdapter;
import org.appointment.service.adapters.AppointmentPriorityLevelAdapter;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "appointment")
public class NewAppointment {
    @HasValue(message = Messages.REQUIRED_CONTRACTORS_NAME)
    private String contractorsName;

    @HasValue(message = Messages.REQUIRED_CONTRACTORS_USER_ID)
    private String contractorsUserId;

    @HasValue(message = Messages.REQUIRED_ROOM_NUMBER)
    private String roomNumber;

    @HasValue(message = Messages.REQUIRED_ISSUE)
    private String issue;

    @NotNull(message = Messages.REQUIRED_DATE)
    private LocalDate date;


    public String getContractorsName() {
        return contractorsName;
    }

    public void setContractorsName(String contractorsName) {
        this.contractorsName = contractorsName;
    }

    public String getContractorsUserId() {
        return contractorsUserId;
    }

    public void setContractorsUserId(String contractorsUserId) {
        this.contractorsUserId = contractorsUserId;
    }


    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @XmlJavaTypeAdapter(AppointmentPriorityLevelAdapter.class)
    public AppointmentPriorityLevelAdapter getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(AppointmentPriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    @XmlJavaTypeAdapter(AppointmentTypeAdapter.class)
    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

}
