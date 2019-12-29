package org.appointment.web.models;

import org.appointment.web.adapters.AppointmentUpdateOperationAdapter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "updateRequest")
public class AppointmentUpdateRequest {
    private AppointmentUpdateOperation operation;

    @XmlJavaTypeAdapter(AppointmentUpdateOperationAdapter.class)
    public AppointmentUpdateOperation getOperation() {
        return operation;
    }

    public void setOperation(AppointmentUpdateOperation operation) {
        this.operation = operation;
    }
}
