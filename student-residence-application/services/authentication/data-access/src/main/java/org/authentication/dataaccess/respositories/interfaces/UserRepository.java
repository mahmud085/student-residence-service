package org.authentication.dataaccess.respositories.interfaces;

import org.authentication.dataaccess.data.models.User;

/**
 * UserRepository is the repository interface for User.
 *
 * @author Imtiaz Abedin
 *
 */
public interface UserRepository extends GenericRepository<User> {
    /**
     * Retreives the user associated with user_id.
     * @param userId of the user.
     * @return User object for the specified parameters. Returns null if no active user is found with the specified parameters.
     */
    User getUserById(String userId);

    /**
     * Retreives the user associated with user_id and password.
     * @param userId of the user.
     * @param password of the user.
     * @return User object for the specified parameters. Returns null if no active user is found with the specified parameters.
     */
    User getUserByIdPass(String userId, String password);
}
