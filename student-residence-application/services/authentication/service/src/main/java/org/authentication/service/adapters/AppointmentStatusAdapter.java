package org.authentication.service.adapters;

import org.authentication.dataaccess.data.enums.AppointmentStatus;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class AppointmentStatusAdapter extends XmlAdapter<String, AppointmentStatus> {

    @Override
    public AppointmentStatus unmarshal(String statusString) {
        return Arrays.stream(AppointmentStatus.values())
                .filter(x -> x.name().equalsIgnoreCase(statusString))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String marshal(AppointmentStatus status) {
        return status != null ? status.name() : null;
    }
}
