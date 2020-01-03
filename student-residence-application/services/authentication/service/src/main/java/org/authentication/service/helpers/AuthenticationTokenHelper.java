package org.authentication.service.helpers;

import com.google.inject.Inject;
import io.jsonwebtoken.Jwts;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;
import java.security.Key;
import java.util.List;


public class AuthenticationTokenHelper {

    @Inject
    private KeyGenerator keyGenerator;

    public void checkAuthenticationToken(HttpHeaders headers) {
        List<String> headerList = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        String authorizationHeader;
        try {
            authorizationHeader = headerList.get(0);
        } catch (Exception e) {
            /*return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.AUTHORIZATION_FAILED_CAUSE_NO_AUTHORIZATION_HEADER);*/
            throw new NotAuthorizedException("Accurate Authorization header must be provided");
        }
     /*   logger.info("#### authorizationHeader : " + authorizationHeader);*/
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
          /*  logger.severe("#### invalid authorizationHeader : " + authorizationHeader);*/
            throw new NotAuthorizedException("Accurate Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {

            // Validate the token
            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
      /*      logger.info("#### valid token : " + token);*/

        } catch (Exception e) {
            /*logger.severe("#### invalid token : " + token);*/
            /*return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.INVALID_ACCESS_TOKEN);*/
        }

    }
}