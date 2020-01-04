package org.authentication.web.authorization;

import com.google.inject.Inject;
import org.authentication.common.Messages;
import org.authentication.common.exceptions.InvalidAccessTokenException;
import org.authentication.common.exceptions.ObjectNotFoundException;
import org.authentication.common.exceptions.ValidationException;
import org.authentication.dataaccess.data.enums.UserType;
import org.authentication.dataaccess.data.models.User;
import org.authentication.web.helpers.JwtHelper;
import org.authentication.service.services.interfaces.UserService;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    @Inject
    UserService userService;

    @Context
    private ResourceInfo resourceInfo;

    private final String AUTHENTICATION_SCHEME = "Bearer";

    private String[] rolesAllowed;

    private String accessToken, authenticationHeaderValue;

    private User contextUser;

    private ContainerRequestContext requestContext;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        this.requestContext = requestContext;

        try {
            if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
                return;
            }

            if (resourceInfo.getResourceMethod().getAnnotation(PermitAll.class) != null) {
                return;
            }

            rolesAllowed = resourceInfo.getResourceMethod()
                    .getAnnotation(RolesAllowed.class)
                    .value();

            authenticationHeaderValue = requestContext.getHeaderString("Authorization");

            validateAuthHeaderValue(authenticationHeaderValue);

            JwtHelper.validateToken(accessToken);

            contextUser = userService.getUserByAccessToken(accessToken);

            if (contextUser == null) {
                abort(Response.Status.BAD_REQUEST, Messages.INVALID_OR_EXPIRED_ACCESS_TOKEN);
                return;
            }

            if (!isRoleAllowed(rolesAllowed, contextUser.getUserType())) {
                abort(Response.Status.UNAUTHORIZED, Messages.AUTHORIZATION_FAILED_USER_ROLE_NOT_ALLOWED);
                return;
            }

            setSecurityContext();
        } catch (InvalidAccessTokenException | ObjectNotFoundException | ValidationException ex) {
            abort(Response.Status.BAD_REQUEST, ex.getMessage());
            return;
        } catch (Exception ex) {
            abort(Response.Status.INTERNAL_SERVER_ERROR, Messages.INTERNAL_ERROR);
            return;
        }
    }

    private void validateAuthHeaderValue(String value) throws ValidationException {
        if (value == null || value.isEmpty()) {
            throw new ValidationException("Authorization header value is required.");
        }

        String[] headerParts = value.trim().split(" ");

        if (!headerParts[0].equals(AUTHENTICATION_SCHEME)) {
            throw new ValidationException("Bearer authentication is required.");
        }

        if (headerParts.length < 2 || headerParts[1].isEmpty()) {
            throw new ValidationException("Access Token is required.");
        }

        accessToken = headerParts[1];
    }

    private boolean isRoleAllowed(String[] rolesAllowed, UserType contextUserRole) {
        for (String role : rolesAllowed) {
            if (contextUserRole == UserType.valueOf(role)) {
                return true;
            }
        }

        return false;
    }

    private void abort(Response.Status status, String message) {
        requestContext.abortWith(
                Response.status(status)
                        .entity(message)
                        .build()
        );
    }

    private void setSecurityContext() {
        this.requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        return contextUser.getUserId();
                    }
                };
            }

            @Override
            public boolean isUserInRole(String role) {
                return contextUser.getUserType() == UserType.valueOf(role);
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return authenticationHeaderValue;
            }
        });
    }
}
