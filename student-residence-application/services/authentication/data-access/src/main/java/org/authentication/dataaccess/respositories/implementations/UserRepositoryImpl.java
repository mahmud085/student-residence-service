package org.authentication.dataaccess.respositories.implementations;

import org.authentication.common.Messages;
import org.authentication.common.exceptions.PaginationRangeOutOfBoundException;
import org.authentication.common.helpers.PaginationHelper;
import org.authentication.dataaccess.data.enums.AppointmentStatus;
import org.authentication.dataaccess.data.models.Appointment;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.data.models.User;
import org.authentication.dataaccess.models.PaginatedDataList;
import org.authentication.dataaccess.respositories.interfaces.AppointmentRepository;
import org.authentication.dataaccess.respositories.interfaces.UserRepository;
import org.authentication.dataaccess.store.DataStore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {
    private static int id = 0;


    @Override
    public User add(User obj) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    private synchronized int getNewId() {
        return ++id;
    }

    @Override
    public User getUserById(String userId) {
        for (User user : DataStore.user) {
            if (user.getUserId().equals(userId)) {
                return user;
            }

        }
        return null;
    }

    @Override
    public User getUserIdByAccessToken(String accessToken) {
        for (Authentication authentication : DataStore.authentications) {
            if (authentication.getAccessToken().equals(accessToken)) {
            return getUserById(authentication.getUserId());
        }

    }
        return null;
    }

    @Override
    public User getUserByIdPass(String userId, String password) {
        for (User user : DataStore.user) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
/*				for(Authentication authentication : DataStore.authentications){
					if(authentication.getUserId().equals(userId)) {
						return authentication;
					}

				}*/
                return user;
            }

        }
        return null;
    }
}
