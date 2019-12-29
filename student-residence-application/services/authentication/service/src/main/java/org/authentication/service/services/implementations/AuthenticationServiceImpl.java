package org.authentication.service.services.implementations;

import com.google.inject.Inject;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.respositories.interfaces.AuthenticationRepository;
import org.authentication.service.models.NewAuthentication;

import javax.ws.rs.client.Client;
import java.util.UUID;


public class AuthenticationServiceImpl implements org.authentication.service.services.interfaces.AuthenticationService {
	@Inject
	private AuthenticationRepository authenticationRepository;

	private Client client;

	@Override
	public Authentication addAuthenticatedUser(NewAuthentication newAuthentication) {
		Authentication authentication = new Authentication() {
			{
				setUserId(newAuthentication.getUserId());
				setAccessToken(newAuthentication.getAccessToken());
				setGeneratedTime(newAuthentication.getGeneratedTime());
				setExpiryTime(newAuthentication.getExpiryTime());

			}
		};
		return authenticationRepository.add(authentication);
	}
}
