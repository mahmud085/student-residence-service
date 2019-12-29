package org.authentication.dataaccess.respositories.interfaces;

import org.authentication.dataaccess.data.models.Authentication;


public interface AuthenticationRepository extends GenericRepository<Authentication> {
Authentication getAuthenticationByUserId(String userId);
}
