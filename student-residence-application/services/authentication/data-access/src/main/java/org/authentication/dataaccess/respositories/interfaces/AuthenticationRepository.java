package org.authentication.dataaccess.respositories.interfaces;

import org.authentication.dataaccess.data.models.Authentication;

/**
 * AuthenticationRepository is the repository interface for Authentication.
 *
 * @author Imtiaz Abedin
 *
 */
public interface AuthenticationRepository extends GenericRepository<Authentication> {
    /**
     * Checks the access token associated with user.
     * @param accessToken JWTToken of the user.
     * @return Active Authentication object for the specified parameters. Returns null if no active accessToken is found with the specified parameters.
     */
    Authentication getByAccessToken(String accessToken);
}
