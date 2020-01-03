package org.authentication.web.authorization;

import org.authentication.common.Messages;

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

    private final String AUTHENTICATION_SCHEME = "Bearer";

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (resourceInfo.getResourceMethod()
                .getAnnotation(PermitAll.class) != null) {
            return;
        }

        String[] rolesAllowed = resourceInfo.getResourceMethod()
                .getAnnotation(RolesAllowed.class)
                .value();

        String userId = "dummy";
        String userRole = "Administrator";

        if (!isRoleAllowed(userRole)) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(Messages.AUTHORIZATION_FAILED_USER_ROLE_NOT_ALLOWED)
                            .build());
        }

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return new Principal() {
                    @Override
                    public String getName() {
                        return userId;
                    }
                };
            }

            @Override
            public boolean isUserInRole(String role) {
                return userRole.equalsIgnoreCase(role);
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public String getAuthenticationScheme() {
                return AUTHENTICATION_SCHEME;
            }
        });
    }

    private boolean isRoleAllowed(String role) {
        return true;
    }
}
