package org.appointment.web.adapters;

import org.appointment.web.models.AppointmentUpdateOperation;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class AppointmentUpdateOperationAdapter extends XmlAdapter<String, AppointmentUpdateOperation> {

    @Override
    public AppointmentUpdateOperation unmarshal(String statusString) {
        return Arrays.stream(AppointmentUpdateOperation.values())
                .filter(x -> x.name().equalsIgnoreCase(statusString))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String marshal(AppointmentUpdateOperation status) {
        return status != null ? status.name() : null;
    }
}
