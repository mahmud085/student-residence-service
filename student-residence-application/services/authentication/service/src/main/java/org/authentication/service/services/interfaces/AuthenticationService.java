package org.authentication.service.services.interfaces;

import org.authentication.common.exceptions.InvalidAccessTokenException;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.service.models.NewAuthentication;

import javax.ws.rs.core.UriInfo;

public interface AuthenticationService {
    Authentication addAuthenticatedUser(NewAuthentication newAuthentication);
}