package org.contract.web.models;

import org.contract.dataaccess.data.models.Contract;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "response")
public class ContractListResponse {
    @XmlElementWrapper(name="contracts")
    @XmlElement(name="contract")
    private List<Contract> contracts;
    private PaginationMetadata metadata;

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public PaginationMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(PaginationMetadata metadata) {
        this.metadata = metadata;
    }
}
