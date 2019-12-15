package org.appointment.service.adapters;

import org.appointment.dataaccess.data.enums.AppointmentPriorityLevel;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class AppointmentPriorityLevelAdapter extends XmlAdapter<String, AppointmentPriorityLevel> {

    @Override
    public AppointmentPriorityLevel unmarshal(String priorityLevelString) {
        return Arrays.stream(AppointmentPriorityLevel.values())
                .filter(x -> x.name().equalsIgnoreCase(priorityLevelString))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String marshal(AppointmentPriorityLevel priorityLevel) {
        return priorityLevel != null ? priorityLevel.name() : null;
    }
}
