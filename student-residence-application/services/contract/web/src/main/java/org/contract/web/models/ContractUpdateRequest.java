package org.contract.web.models;

import org.contract.common.adapters.LocalDateAdapter;
import org.contract.web.adapters.ContractUpdateOperationAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "updateRequest")
public class ContractUpdateRequest {
    private ContractUpdateOperation operation;
    private LocalDate endDate;

    @XmlJavaTypeAdapter(ContractUpdateOperationAdapter.class)
    public ContractUpdateOperation getOperation() {
        return operation;
    }

    public void setOperation(ContractUpdateOperation operation) {
        this.operation = operation;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
