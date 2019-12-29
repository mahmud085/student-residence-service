package org.authentication.dataaccess.respositories.interfaces;

import org.authentication.common.exceptions.PaginationRangeOutOfBoundException;
import org.authentication.dataaccess.data.enums.AppointmentStatus;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.data.models.User;
import org.authentication.dataaccess.models.PaginatedDataList;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends GenericRepository<User> {
    User getUserById(String userId);
    User getUserIdByAccessToken(String accessToken);
    User getUserByIdPass(String userId, String password);
}
