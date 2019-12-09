package org.contract.service.models;

import org.contract.common.Constants;
import org.contract.common.Messages;
import org.contract.common.adapters.LocalDateAdapter;
import org.contract.common.validation.annotations.HasValue;
import org.contract.dataaccess.data.enums.ContractStatus;
import org.contract.service.adapters.ContractStatusAdapter;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "contract")
public class NewContract {
    @HasValue(message = Messages.REQUIRED_CONTRACTORS_NAME)
    private String contractorsName;

    @Pattern(regexp = Constants.REGEX_VALID_EMAIL, message = Messages.INVALID_EMAIL)
    private String contractorsEmail;

    @Pattern(regexp = Constants.REGEX_VALID_PHONE, message = Messages.INVALID_PHONE)
    private String contractorsPhone;

    @HasValue(message = Messages.REQUIRED_ROOM_NUMBER)
    private String roomNumber;

    @NotNull(message = Messages.REQUIRED_START_DATE)
    private LocalDate startDate;

    @NotNull(message = Messages.REQUIRED_END_DATE)
    private LocalDate endDate;

    @NotNull(message = Messages.REQUIRED_STATUS)
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

    @XmlJavaTypeAdapter(ContractStatusAdapter.class)
    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }
}
