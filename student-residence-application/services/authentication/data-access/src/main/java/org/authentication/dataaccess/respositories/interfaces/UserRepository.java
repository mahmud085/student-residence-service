package org.authentication.dataaccess.respositories.interfaces;

import org.authentication.dataaccess.data.models.User;

public interface UserRepository extends GenericRepository<User> {
    User getUserById(String userId);
    User getUserByIdPass(String userId, String password);
}
