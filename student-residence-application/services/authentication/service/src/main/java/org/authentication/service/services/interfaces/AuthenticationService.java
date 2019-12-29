package org.authentication.service.services.interfaces;

import org.authentication.common.exceptions.InvalidOperationException;
import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.common.exceptions.PaginationRangeOutOfBoundException;
import org.authentication.common.exceptions.ValidationException;
import org.authentication.dataaccess.data.models.Appointment;
import org.authentication.dataaccess.data.models.User;
import org.authentication.dataaccess.models.PaginatedDataList;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.service.models.NewAppointment;
import org.authentication.service.models.NewAuthentication;

import java.time.LocalDate;
import java.util.List;

public interface AuthenticationService {
Authentication addAuthenticatedUser(NewAuthentication newAuthentication);
}