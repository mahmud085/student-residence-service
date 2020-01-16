package org.authentication.service.services.interfaces;

import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.dataaccess.data.models.User;
import org.authentication.service.models.RegisterUser;
/**
 * UserService is the service interface for contract.
 *
 * @author Imtiaz Abedin
 *
 */
public interface UserService {

    /**
     * Retreives a user by accesstoken.
     * @param accessToken  created for user.
     * @return Retreived user.
     */
    User getUserByAccessToken(String accessToken) throws ObjectNotFoundException;

    /**
     * Retreives a user by user id.
     * @param userId  containing user id.
     * @return retreived user.
     */
    User getUser(String userId) throws ObjectNotFoundException;

    /**
     * Creates a user.
     * @param newUser Object containing created.
     * @return Created user.
     */
    User registerUser(RegisterUser newUser);

    /**
     * Creates a authentication token.
     * @param userId contains user id.
     * @param password  containing user password.
     * @return Created user.
     */
    User createLoginRequest(String userId, String password) throws ObjectNotFoundException;
}