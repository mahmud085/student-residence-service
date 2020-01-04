package org.authentication.web.resources.interfaces;

import org.authentication.service.models.NewAppointment;
import org.authentication.service.models.NewUser;
import org.authentication.service.models.RegisterUser;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public interface UserResource {
    Response getUserById(String userId);
    Response createUser(RegisterUser newUser);


}
