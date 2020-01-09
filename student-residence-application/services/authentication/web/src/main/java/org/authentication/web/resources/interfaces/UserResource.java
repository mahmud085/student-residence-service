package org.authentication.web.resources.interfaces;

import org.authentication.service.models.RegisterUser;

import javax.ws.rs.core.Response;

public interface UserResource {
    Response getUser(String userId);
    Response createUser(RegisterUser newUser);


}
