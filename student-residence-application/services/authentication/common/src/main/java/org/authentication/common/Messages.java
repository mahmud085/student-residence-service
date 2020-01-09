package org.authentication.common;

public class Messages {
    public static final String INTERNAL_ERROR = "An internal error occurred.";
    public static final String REQUEST_BODY_REQUIRED = "Request body is required.";
    public static final String REQUIRED_USER_ID = "Valid User Id is required.";
    public static final String REQUIRED_USER_EMAIL = "Valid User Email is required.";
    public static final String REQUIRED_USER_PASSWORD = "Valid User Password is required.";
    public static final String USER_NOT_FOUND="User Not Found With this ID.";
    public static final String USER_MISMATCH="User Not Found With this ID and Password.";

    public static final String INVALID_ACCESS_TOKEN = "Access Token is invalid.";
    public static final String INVALID_OR_EXPIRED_ACCESS_TOKEN = "Access Token is invalid/expired. Please login again to refresh the access token.";

    public static final String AUTHORIZATION_FAILED_USER_ROLE_NOT_ALLOWED = "This user's role is not allowed to perform this action.";

    public static final String USER_NOT_AUTHORISED_TO_OPERATE_RESOURCE = "This User is not authorized to perform this operation on this resource.";
}
