package org.authentication.web.resources.implementations;

import com.google.inject.Inject;
import org.authentication.common.Messages;
import org.authentication.common.exceptions.InvalidOperationException;
import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.common.exceptions.ValidationException;
import org.authentication.dataaccess.data.models.Authentication;
import org.authentication.dataaccess.data.models.User;
import org.authentication.service.models.AccessToken;
import org.authentication.service.models.NewAuthentication;
import org.authentication.service.models.LoginRequest;
import org.authentication.service.services.interfaces.AuthenticationService;
import org.authentication.service.services.interfaces.UserService;
import org.authentication.web.Constants;

import org.authentication.web.resources.interfaces.AuthenticationResource;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.time.LocalDate;
import java.util.logging.Logger;

@Path(Constants.RESOURCE_PATH_AUTHENTICATION)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AuthenticationResourceImpl implements AuthenticationResource {
    @Inject
    private UserService userService;

    @Inject
    private AuthenticationService authenticationService;


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
    @PermitAll
    @Path("login")
    public Response createLoginRequest(LoginRequest loginRequest) {
        try {
            logger.info("#### log : " + loginRequest);
            if (loginRequest == null) {
                return buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
            }

            try {
                String token = "";
                User user = userService.createLoginRequest(loginRequest.getUserId(), loginRequest.getPassword());
                if (user != null) {
                    token = authenticationService.issueToken(loginRequest.getUserId(), uriInfo);
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
                    AccessToken createdAccessToken = new AccessToken() {
                        {
                            setAccessToken(finalToken);
                        }
                    };
                    // Return the token on the response
                    return buildResponseObject(Response.Status.OK, createdAccessToken);
                }

                return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.USER_MISMATCH);
            } catch (ValidationException | InvalidOperationException | ObjectNotFoundException e) {
                // TODO Auto-generated catch block
                return buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
            }
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    @GET
    @PermitAll
    @Path("accessToken/{accessToken}/validation")

    public Response getUserByAccessToken(@PathParam("accessToken") String accessToken) {
        if (accessToken == null || accessToken.length() == 0) {
            return buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
        }

        try {

            User user = userService.getUserByAccessToken(accessToken);

            return buildResponseObject(Response.Status.OK, user);
        } catch (ValidationException | InvalidOperationException | ObjectNotFoundException e) {
            // TODO Auto-generated catch block
            return buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }


}
