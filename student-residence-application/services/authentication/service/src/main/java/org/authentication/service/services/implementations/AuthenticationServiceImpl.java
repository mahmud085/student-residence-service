package org.authentication.service.services.implementations;

import com.google.inject.Inject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.respositories.interfaces.AuthenticationRepository;
import org.authentication.service.helpers.KeyGenerator;
import org.authentication.service.models.NewAuthentication;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;


public class AuthenticationServiceImpl implements org.authentication.service.services.interfaces.AuthenticationService {
	@Inject
	private AuthenticationRepository authenticationRepository;

	private Client client;

	@Inject
	private KeyGenerator keyGenerator;

	@Inject
	private Logger logger;

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

	@Override
	public String issueToken(String login, UriInfo uriInfo) {
		Key key = keyGenerator.generateKey();
		String jwtToken = Jwts.builder()
				.setSubject(login)
				.setIssuer(uriInfo.getAbsolutePath().toString())
				.setIssuedAt(new Date())
				.setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();
		logger.info("#### generating token for a key : " + jwtToken + " - " + key);
		return jwtToken;
	}

	private Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
