package org.contract.web.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.contract.dataaccess.data.models.Contract;

import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class ContractResponse extends Contract {
    @XmlElementWrapper(name="links")
    @JacksonXmlProperty(localName = "link")
    private List<Hateoas> links;

    public List<Hateoas> getLinks() {
        return links;
    }

    public void setLinks(List<Hateoas> links) {
        this.links = links;
    }
}
