package org.authentication.web.resources.interfaces;

import org.authentication.service.models.LoginRequest;

import javax.ws.rs.core.Response;

public interface AuthenticationResource {
    Response createLoginRequest(LoginRequest loginRequest);
    Response getUserByAccessToken(String accessToken);
}
