package org.authentication.service.adapters;

import org.authentication.dataaccess.data.enums.AppointmentType;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class AppointmentTypeAdapter extends XmlAdapter<String, AppointmentType> {

    @Override
    public AppointmentType unmarshal(String appointmentTypeString) {
        return Arrays.stream(AppointmentType.values())
                .filter(x -> x.name().equalsIgnoreCase(appointmentTypeString))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String marshal(AppointmentType appointmentType) {
        return appointmentType != null ? appointmentType.name() : null;
    }
}
