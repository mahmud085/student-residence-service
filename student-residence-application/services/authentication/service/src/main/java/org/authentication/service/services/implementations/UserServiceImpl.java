package org.authentication.service.services.implementations;

import com.google.inject.Inject;
import org.authentication.common.Messages;
import org.authentication.common.exceptions.InvalidOperationException;
import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.common.exceptions.PaginationRangeOutOfBoundException;
import org.authentication.common.exceptions.ValidationException;
import org.authentication.common.helpers.DateHelper;
import org.authentication.common.helpers.ValidationHelper;
import org.authentication.dataaccess.data.enums.AppointmentStatus;
import org.authentication.dataaccess.data.enums.AppointmentType;
import org.authentication.dataaccess.data.models.Appointment;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.data.models.User;
import org.authentication.dataaccess.models.PaginatedDataList;
import org.authentication.dataaccess.respositories.interfaces.AppointmentRepository;
import org.authentication.dataaccess.respositories.interfaces.UserRepository;
import org.authentication.service.models.Contract;
import org.authentication.service.models.NewAppointment;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class UserServiceImpl implements org.authentication.service.services.interfaces.UserService {
    @Inject
    private UserRepository userRepository;

    private Client client;


    @Override
    public User getUserByAccessToken(String accessToken, String contextUserId) throws ValidationException, InvalidOperationException, ObjectNotFoundException {
		User user = userRepository.getUserIdByAccessToken(accessToken);
		if (user == null)
			throw new ObjectNotFoundException(Messages.USER_NOT_FOUND);

		return user;
    }

    @Override
    public User getUser(String userId, String contextUserI) throws ValidationException, InvalidOperationException, ObjectNotFoundException {
        User user = userRepository.getUserById(userId);
        if (user == null)
            throw new ObjectNotFoundException(Messages.USER_NOT_FOUND);

        return user;
    }

    @Override
    public User createLoginRequest(String userId, String password) throws ValidationException, InvalidOperationException, ObjectNotFoundException {
    	System.out.println("Entered Here");
    	
    	User user = userRepository.getUserByIdPass(userId, password);
        if (user == null)
            throw new ObjectNotFoundException(Messages.USER_NOT_FOUND);

        return user;
    }
}
