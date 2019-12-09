package org.contract.service.adapters;

import org.contract.dataaccess.data.enums.ContractStatus;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class ContractStatusAdapter extends XmlAdapter<String, ContractStatus> {

    @Override
    public ContractStatus unmarshal(String statusString) {
        return Arrays.stream(ContractStatus.values())
                .filter(x -> x.name().equalsIgnoreCase(statusString))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String marshal(ContractStatus status) {
        return status != null ? status.name() : null;
    }
}
