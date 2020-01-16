package org.appointment.web.resources.interfaces;

import javax.ws.rs.core.Response;

public interface ContractorResource {
    Response getAppointmentsOfContractor(String contractorsUserId);
}
