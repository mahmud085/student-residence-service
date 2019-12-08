package org.contract.service.models;

import org.contract.dataaccess.adapters.LocalDateAdapter;
import org.contract.dataaccess.models.ContractStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "contract")
public class NewContract {
    @NotNull(message = "Contractors Name is required.")
    private String contractorsName;
    @NotNull(message = "Contractors Email is required.")
    @Email(message = "Valid email is required.")
    private String contractorsEmail;
    @NotNull(message = "Contractors Phone is required.")
    private String contractorsPhone;
    @NotNull(message = "Room Number is required.")
    private String roomNumber;
    @NotNull(message = "Start Date is required.")
    private LocalDate startDate;
    @NotNull(message = "End Date is required.")
    private LocalDate endDate;
    @NotNull(message = "Status is required.")
    private ContractStatus status;

    public String getContractorsName() {
        return contractorsName;
    }

    public void setContractorsName(String contractorsName) {
        this.contractorsName = contractorsName;
    }

    public String getContractorsEmail() {
        return contractorsEmail;
    }

    public void setContractorsEmail(String contractorsEmail) {
        this.contractorsEmail = contractorsEmail;
    }

    public String getContractorsPhone() {
        return contractorsPhone;
    }

    public void setContractorsPhone(String contractorsPhone) {
        this.contractorsPhone = contractorsPhone;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }
}
