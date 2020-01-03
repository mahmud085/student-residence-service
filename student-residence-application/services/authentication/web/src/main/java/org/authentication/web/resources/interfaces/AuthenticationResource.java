package org.authentication.web.resources.interfaces;

import org.authentication.service.models.NewUser;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public interface AuthenticationResource {
    Response createLoginRequest(NewUser newUser);
    Response getUserByAccessToken(String accessToken);
}
