package org.authentication.service.services.interfaces;

import org.authentication.common.exceptions.InvalidAccessTokenException;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.service.models.NewAuthentication;

import javax.ws.rs.core.UriInfo;
/**
 * AuthenticationService is the repository interface for Authentication.
 *
 * @author Imtiaz Abedin
 *
 */
public interface AuthenticationService {

    /**
     * Create authentication credentials.
     * @param newAuthentication Object containing data for the authentication to be created.
     * @return Created authentication.
     */
    Authentication addAuthenticatedUser(NewAuthentication newAuthentication);
}