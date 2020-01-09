package org.authentication.web.resources.implementations;

import com.google.inject.Inject;
import org.authentication.common.Messages;
import org.authentication.common.exceptions.InvalidAccessTokenException;
import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.dataaccess.data.models.User;
import org.authentication.service.models.AccessToken;
import org.authentication.service.models.LoginRequest;
import org.authentication.service.models.NewAuthentication;
import org.authentication.service.services.interfaces.AuthenticationService;
import org.authentication.service.services.interfaces.UserService;
import org.authentication.web.Constants;
import org.authentication.web.helpers.JwtHelper;
import org.authentication.web.resources.interfaces.AuthenticationResource;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Path(Constants.RESOURCE_PATH_AUTHENTICATION)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AuthenticationResourceImpl implements AuthenticationResource {
    @Inject
    private UserService userService;

    @Inject
    private AuthenticationService authenticationService;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @Override
    @POST
    @PermitAll
    @Path("login")
    public Response createLoginRequest(LoginRequest loginRequest) {
        try {
            if (loginRequest == null) {
                return buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
            }

            try {
                User user = userService.createLoginRequest(loginRequest.getUserId(), loginRequest.getPassword());
                if (user != null) {
                    LocalDateTime tokenExpiresAt = LocalDateTime.now().plusHours(2);

                    String finalToken = JwtHelper.issueToken(
                            loginRequest.getUserId(),
                            uriInfo.getAbsolutePath().toString(),
                            Date.from(tokenExpiresAt.atZone(ZoneId.systemDefault()).toInstant()));

                    NewAuthentication currentUserAuthentication = new NewAuthentication() {
                        {
                            setUserId(user.getUserId());
                            setAccessToken(finalToken);
                            setGeneratedTime(LocalDateTime.now());
                            setExpiryTime(tokenExpiresAt);

                        }
                    };
                    authenticationService.addAuthenticatedUser(currentUserAuthentication);
                    AccessToken createdAccessToken = new AccessToken() {
                        {
                            setAccessToken(finalToken);
                        }
                    };

                    return buildResponseObject(Response.Status.OK, createdAccessToken);
                }

                return buildResponseObject(Response.Status.BAD_REQUEST, Messages.USER_MISMATCH);
            } catch (ObjectNotFoundException e) {
                return buildResponseObject(Response.Status.BAD_REQUEST, Messages.USER_MISMATCH);
            }
        } catch (Exception e) {
            return buildResponseObject(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
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
            JwtHelper.validateToken(accessToken);

            User user = userService.getUserByAccessToken(accessToken);

            return buildResponseObject(Response.Status.OK, user);
        } catch (InvalidAccessTokenException | ObjectNotFoundException e) {
            return buildResponseObject(Response.Status.BAD_REQUEST, Messages.INVALID_OR_EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            return buildResponseObject(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private <T> Response buildResponseObject(Response.Status status, T entity) {
        return Response.status(status)
                .entity(entity)
                .build();
    }
}
