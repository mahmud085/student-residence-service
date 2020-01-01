package org.authentication.web.resources.implementations;

import com.google.inject.Inject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.authentication.common.Messages;
import org.authentication.common.exceptions.InvalidOperationException;
import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.common.exceptions.ValidationException;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.data.models.User;
import org.authentication.service.models.NewAuthentication;
import org.authentication.service.models.NewUser;
import org.authentication.service.services.interfaces.AuthenticationService;
import org.authentication.service.services.interfaces.UserService;
import org.authentication.web.Constants;
import org.authentication.web.filter.JWTTokenNeeded;
import org.authentication.web.helpers.KeyGenerator;
import org.authentication.web.resources.interfaces.AuthenticationResource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path(Constants.RESOURCE_PATH_AUTHENTICATION)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AuthenticationResourceImpl implements AuthenticationResource {
    @Inject
    private UserService userService;

    @Inject
    private AuthenticationService authenticationService;

    @Inject
    private KeyGenerator keyGenerator;

    @Inject
    private Logger logger;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;


    private <T> Response buildResponseObject(Response.Status status, T entity) {
        return Response.status(status)
                .entity(entity)
                .build();
    }


    @Override
    @POST
    @RolesAllowed({Constants.ROLE_Resident, Constants.ROLE_Caretaker})
    @Path("login")
    public Response createLoginRequest(NewUser newUser) {
        String contextUserId = securityContext.getUserPrincipal().getName();
        if (newUser == null) {
            return buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
        }

        try {
            String token = "";
            User user = userService.createLoginRequest(newUser.getUserId(), newUser.getPassword());
            if (user != null) {
                token = issueToken(newUser.getUserId());
                String finalToken = token;
                NewAuthentication currentUserAuthentication = new NewAuthentication() {
                    {
                        setUserId(user.getUserId());
                        setAccessToken(finalToken);
                        setGeneratedTime(LocalDate.now());
                        setExpiryTime(LocalDate.now().plusDays(10));

                    }
                };
                Authentication storedCurrentUserAuthentication = authenticationService.addAuthenticatedUser(currentUserAuthentication);
                logger.info("#### authorizationHeader : " + storedCurrentUserAuthentication);
            }
            // Return the token on the response
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        } catch (ValidationException | InvalidOperationException | ObjectNotFoundException e) {
            // TODO Auto-generated catch block
            return buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    @GET
    @RolesAllowed({Constants.ROLE_Resident})
    @Path("accessToken/{accessToken}")

    public Response getUserByAccessToken(@PathParam("accessToken") String accessToken, @Context HttpHeaders headers) {
        String authCheck = checkAuthenticationToken(headers);
        if (authCheck.equals("401-1")) {
            return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.AUTHORIZATION_FAILED_CAUSE_NO_AUTHORIZATION_HEADER);
        } else if (authCheck.equals("401-2")) {
            return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.INVALID_ACCESS_TOKEN);
        }
        String contextUserId = securityContext.getUserPrincipal().getName();
        if (accessToken == null || accessToken.length() == 0) {
            return buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
        }

        try {

            User user = userService.getUserByAccessToken(accessToken, contextUserId);

            return buildResponseObject(Response.Status.OK, user);
        } catch (ValidationException | InvalidOperationException | ObjectNotFoundException e) {
            // TODO Auto-generated catch block
            return buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }

    private String issueToken(String login) {
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

    public String checkAuthenticationToken(HttpHeaders headers) {
        /*List<String> headerList = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);*/

        // Check if the HTTP Authorization header is present and formatted correctly
        String authorizationHeader;
        try {
            authorizationHeader = headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0);
        } catch (Exception e) {
            return "401-1";
        }
        logger.info("#### authorizationHeader : " + authorizationHeader);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.severe("#### invalid authorizationHeader : " + authorizationHeader);
            throw new NotAuthorizedException("Accurate Authorization header must be provided");
        }
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {
            // Validate the token
            Key key = keyGenerator.generateKey();
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            logger.info("#### valid token : " + token);
            return "200";

        } catch (Exception e) {
            logger.severe("#### invalid token : " + token);
            return "401-2";
        }

    }


}
