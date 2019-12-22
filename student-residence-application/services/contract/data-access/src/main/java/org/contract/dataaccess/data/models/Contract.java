package org.contract.dataaccess.data.models;

import org.contract.common.adapters.LocalDateAdapter;
import org.contract.dataaccess.data.enums.ContractStatus;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@javax.persistence.Entity
@Table(name = "contract")
@NamedQueries({
        @NamedQuery(name = "Contract.getCount",
                query = "select count(c.id) from Contract c"),

        @NamedQuery(name = "Contract.getAll",
                query = "select c from Contract c"),

        @NamedQuery(name = "Contract.findSingleByContractId",
                query = "select c from Contract c where c.contractId like :contractId"),

        @NamedQuery(name = "Contract.findMultipleByContractorsUserId",
                query = "select c from Contract c where c.contractorsUserId like :contractorsUserId"),

        @NamedQuery(name = "Contract.filterByContractorsName",
                query = "select c from Contract c where c.contractorsName like concat('%', :contractorsNameFilterText, '%')"),

        @NamedQuery(name = "Contract.getCountFilterByContractorsName",
                query = "select count(c.id) from Contract c where c.contractorsName like concat('%', :contractorsNameFilterText, '%')"),

        @NamedQuery(name = "Contract.findActiveContractForRoomByDates",
                query = "select c from Contract c " +
                        "where c.roomNumber like :roomNumber " +
                        "and ((:startDate between c.startDate and c.endDate) or (:endDate between c.startDate and c.endDate) or (:startDate < c.startDate and :endDate > c.endDate)) " +
                        "and (c.status = :confirmedStatus or c.createdOn >= :earliestPossibleCreatedOnForPending)")
})
@XmlRootElement(name = "contract")
public class Contract extends Entity {
    private String contractId;
    private String contractorsName;
    private String contractorsUserId;
    private String contractorsEmail;
    private String contractorsPhone;
    private String roomNumber;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private ContractStatus status;
    private String createdBy;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate createdOn;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

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
}
