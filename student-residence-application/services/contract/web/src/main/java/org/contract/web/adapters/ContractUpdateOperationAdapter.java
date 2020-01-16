package org.contract.web.adapters;

import org.contract.web.models.ContractUpdateOperation;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class ContractUpdateOperationAdapter extends XmlAdapter<String, ContractUpdateOperation> {

    @Override
    public ContractUpdateOperation unmarshal(String statusString) {
        return Arrays.stream(ContractUpdateOperation.values())
                .filter(x -> x.name().equalsIgnoreCase(statusString))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String marshal(ContractUpdateOperation status) {
        return status != null ? status.name() : null;
    }
}
