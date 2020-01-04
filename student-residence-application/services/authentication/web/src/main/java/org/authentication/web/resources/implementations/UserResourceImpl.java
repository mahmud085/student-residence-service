package org.authentication.web.resources.implementations;

import com.google.inject.Inject;
import org.authentication.common.Messages;
import org.authentication.common.exceptions.*;
import org.authentication.dataaccess.data.models.User;
import org.authentication.service.models.RegisterUser;
import org.authentication.service.models.UserRole;
import org.authentication.service.services.interfaces.UserService;
import org.authentication.web.Constants;

import org.authentication.web.resources.interfaces.UserResource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.logging.Logger;

@Path(Constants.RESOURCE_PATH_USER)
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class UserResourceImpl implements UserResource {
    @Inject
    private UserService userService;
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
    @GET
    @RolesAllowed({Constants.ROLE_ADMIN})
    @Path("{user-id}")
    public Response getUserById(@PathParam("user-id") String userId) {
        if (userId == null || userId.length() == 0) {
            return buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
        }

        try {

            User user = userService.getUser(userId);

            return buildResponseObject(Response.Status.OK, user);
        } catch (ValidationException | InvalidOperationException | ObjectNotFoundException e) {
            // TODO Auto-generated catch block
            return buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return buildResponseObject(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @POST
    @RolesAllowed({Constants.ROLE_ADMIN})
    public Response createUser(RegisterUser newUser) {
        if (newUser == null ) {
            return buildResponseObject(Response.Status.BAD_REQUEST, Messages.REQUEST_BODY_REQUIRED);
        }

        try {
            newUser.setPassword("autogenerated");
            newUser.setName("doesNotMatter");
            newUser.setRole(UserRole.Resident);

            User user = userService.registerUser(newUser);

            return buildResponseObject(Response.Status.OK, user);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return buildResponseObject(Response.Status.BAD_REQUEST, e.getMessage());
        }
    }
}
