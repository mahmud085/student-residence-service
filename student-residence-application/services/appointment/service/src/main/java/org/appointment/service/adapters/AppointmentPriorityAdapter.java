package org.appointment.service.adapters;

import org.appointment.dataaccess.data.enums.AppointmentPriority;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Arrays;

public class AppointmentPriorityAdapter extends XmlAdapter<String, AppointmentPriority> {

    @Override
    public AppointmentPriority unmarshal(String priorityString) {
        return Arrays.stream(AppointmentPriority.values())
                .filter(x -> x.name().equalsIgnoreCase(priorityString))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String marshal(AppointmentPriority priority) {
        return priority != null ? priority.name() : null;
    }
}
