package org.appointment.dataaccess.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.appointment.common.adapters.LocalDateAdapter;
import org.appointment.dataaccess.data.enums.AppointmentPriority;
import org.appointment.dataaccess.data.enums.AppointmentStatus;
import org.appointment.dataaccess.data.enums.AppointmentType;
//import org.appointment.service.adapters.AppointmentPriorityAdapter;
//import org.appointment.service.adapters.AppointmentStatusAdapter;
//import org.appointment.service.adapters.AppointmentTypeAdapter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "appointment")
@javax.persistence.Entity(name="appointment")
@Table(name = "appointment")
@NamedQueries({
	    @NamedQuery( name = "appointment.findAll", query = "select a from appointment a")
	    , @NamedQuery(name ="appointment.getAppointmentById", query = "select a from appointment a where a.appointmentId like :appointmentId ")
	    , @NamedQuery(name ="appointment.findByContract", query = "select a from appointment a where a.contractId = :contractId")
        , @NamedQuery(name ="appointment.findAllByDesiredDate", query = "select a from appointment a where a.desiredDate = :desiredDate")
        , @NamedQuery(name ="appointment.acceptAppointment", query = "update appointment a set a.status= :status where a.appointmentId = :appointmentId")
        , @NamedQuery(name ="appointment.findAllByCreatedBy", query = "select a from appointment a where a.createdBy = :createdByUserId")
        , @NamedQuery(name ="appointment.getCount", query = "select count(a.id) from appointment a")
        , @NamedQuery(name ="appointment.getCountFilterByDesiredDate", query = "select count(a.id) from appointment a where a.desiredDate = :desiredDate")
})
public class Appointment extends Entity implements Cloneable  {
	
	@Column(name = "appointmentId")
    private String appointmentId;
	@Column(name="contractorsName")
    private String contractorsName;
	@Column(name="contractId")
    private String contractId;
	@Column(name = "roomNumber")
    private String roomNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "appointmentType")
    private AppointmentType appointmentType;
	
	@Column(name = "issue")
    private String issue;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @Column(name = "desiredDate")
    private LocalDate desiredDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppointmentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private AppointmentPriority priority;

    @JsonIgnore
    @Column(name = "createdBy")
    private String createdBy;
    
    @Column(name = "createdOn")
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
