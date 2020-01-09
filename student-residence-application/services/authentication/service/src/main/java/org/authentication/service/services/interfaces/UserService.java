package org.authentication.service.services.interfaces;

import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.dataaccess.data.models.User;
import org.authentication.service.models.RegisterUser;

public interface UserService {
    User getUserByAccessToken(String accessToken) throws ObjectNotFoundException;
    User getUser(String userId) throws ObjectNotFoundException;
    User registerUser(RegisterUser newUser);
    User createLoginRequest(String userId, String password) throws ObjectNotFoundException;
}