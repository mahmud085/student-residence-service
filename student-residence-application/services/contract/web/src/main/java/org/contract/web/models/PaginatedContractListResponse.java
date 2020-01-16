package org.contract.web.models;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.contract.dataaccess.data.models.Contract;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "response")
public class PaginatedContractListResponse {
    @XmlElementWrapper(name="contracts")
    @JacksonXmlProperty(localName = "contract")
    @JsonProperty("contracts")
    private List<ContractResponse> contracts;
    private PaginationMetadata metadata;

    public List<ContractResponse> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractResponse> contracts) {
        this.contracts = contracts;
    }

    public PaginationMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(PaginationMetadata metadata) {
        this.metadata = metadata;
    }
}
