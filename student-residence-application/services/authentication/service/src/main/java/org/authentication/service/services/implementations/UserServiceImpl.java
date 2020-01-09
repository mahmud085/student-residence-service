package org.authentication.service.services.implementations;

import com.google.inject.Inject;
import org.authentication.common.Messages;
import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.dataaccess.data.enums.UserType;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.data.models.User;
import org.authentication.dataaccess.respositories.interfaces.AuthenticationRepository;
import org.authentication.dataaccess.respositories.interfaces.UserRepository;
import org.authentication.service.models.RegisterUser;
import org.authentication.service.services.interfaces.UserService;

public class UserServiceImpl implements UserService {
    @Inject
    private AuthenticationRepository authenticationRepository;

    @Inject
    private UserRepository userRepository;

    @Override
    public User getUserByAccessToken(String accessToken) throws ObjectNotFoundException {
        Authentication authentication = authenticationRepository.getByAccessToken(accessToken);

        if (authentication == null)
            throw new ObjectNotFoundException(Messages.INVALID_ACCESS_TOKEN);

		User user = userRepository.getUserById(authentication.getUserId());

		if (user == null)
			throw new ObjectNotFoundException(Messages.USER_NOT_FOUND);

		return user;
    }

    @Override
    public User getUser(String userId) throws ObjectNotFoundException {
        User user = userRepository.getUserById(userId);
        if (user == null)
            throw new ObjectNotFoundException(Messages.USER_NOT_FOUND);

        return user;
    }

    @Override
    public User registerUser(RegisterUser newUser) {
        User userToCreate = new User() {
            {
                setUserId(newUser.getUserId());
                setUserEmail(newUser.getEmail());
                setPassword(newUser.getPassword());
                setUserType(UserType.valueOf(newUser.getRole().toString()));

            }
        };
        return userRepository.add(userToCreate);
    }

    @Override
    public User createLoginRequest(String userId, String password) throws ObjectNotFoundException {
    	System.out.println("Entered Here");
    	User user = userRepository.getUserByIdPass(userId, password);
        if (user == null)
            throw new ObjectNotFoundException(Messages.USER_NOT_FOUND);

        return user;
    }
}
