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
        String authCheck = checkAuthenticationToken(headers);
        if (authCheck.equals("401-1")) {
            return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.AUTHORIZATION_FAILED_CAUSE_NO_AUTHORIZATION_HEADER);
        } else if (authCheck.equals("401-2")) {
            return buildResponseObject(Response.Status.UNAUTHORIZED, Messages.INVALID_ACCESS_TOKEN);
        }
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
