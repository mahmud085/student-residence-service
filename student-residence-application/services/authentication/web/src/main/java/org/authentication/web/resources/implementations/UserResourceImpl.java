package org.authentication.web.resources.implementations;

import com.google.inject.Inject;
import io.jsonwebtoken.Jwts;
import org.authentication.common.Messages;
import org.authentication.common.exceptions.*;
import org.authentication.dataaccess.data.models.User;
import org.authentication.service.services.interfaces.UserService;
import org.authentication.web.Constants;
import org.authentication.web.filter.JWTTokenNeeded;
import org.authentication.web.helpers.AuthenticationTokenHelper;
import org.authentication.web.helpers.KeyGenerator;
import org.authentication.web.resources.interfaces.UserResource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Key;
import java.util.List;
import java.util.logging.Logger;

@Path(Constants.RESOURCE_PATH_USER)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserResourceImpl implements UserResource {
    @Inject
    private UserService userService;
    @Inject
    private Logger logger;
    @Inject
    private KeyGenerator keyGenerator;
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
    @GET
    @RolesAllowed({Constants.ROLE_Resident, Constants.ROLE_Caretaker})
    @Path("{user-id}")
    public Response getUserById(@PathParam("user-id") String userId, @Context HttpHeaders headers) {
                String contextUserId = securityContext.getUserPrincipal().getName();
        if (userId == null || userId.length() == 0) {
            return buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
        }

        try {

            User user = userService.getUser(userId, contextUserId);

            return buildResponseObject(Response.Status.OK, user);
        } catch (ValidationException | InvalidOperationException | ObjectNotFoundException e) {
            // TODO Auto-generated catch block
            return buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }


}
