package org.appointment.common;

public class Messages {
    public static final String INTERNAL_ERROR = "An internal error occurred.";
    public static final String APPOINTMENT_ID_REQUIRED = "Appointment ID is required.";
    public static final String REQUEST_BODY_REQUIRED = "Request body is required.";
    public static final String CONTRACT_NOT_FOUND_WITH_ID = "No contract found with the specified contract ID.";
    public static final String USER_NOT_AUTHORISED_TO_OPERATE_RESOURCE = "This User is not authorized to perform this operation on this resource.";

    public static final String SUCCESSFUL_ACCEPTANCE = "Appointment successfully accepted.";
    public static final String SUCCESSFULLY_DENIED = "Appointment successfully denied.";

    public static final String REQUIRED_CONTRACTORS_NAME = "Contractor's Name is required.";
    public static final String REQUIRED_CONTRACT_ID = "Contract ID is required.";
    public static final String REQUIRED_ROOM_NUMBER = "Room Number is required.";
    public static final String REQUIRED_APPOINTMENT_TYPE = "Valid Appointment Type is required.";
    public static final String REQUIRED_ISSUE = "Valid Issue is required.";
    public static final String REQUIRED_APPOINTMENT_PRIORITY = "Valid Appointment Priority is required.";
    public static final String REQUIRED_DESIRED_DATE = "Desired Date is required.";
    public static final String APPOINTMENT_NOT_FOUND="Appointment Not Found With this ID.";
    public static final String APPOINTMENT_ALREADY_ACCEPTED="Appointment is already accepted.";
    public static final String APPOINTMENT_ALREADY_DENIED="Appointment is already denied.";
    public static final String APPOINTMENT_DATE_EXPIRED="You need to accept appointment before desired date.";

    public static final String INVALID_PAGE_NUM = "Invalid 'pageNum' value.";
    public static final String INVALID_PAGE_SIZE = "Invalid 'pageSize' value.";
    public static final String INVALID_ROOM_NUMBER = "Valid Room Number is required.";
    public static final String INVALID_CONTRACT = "Contract is not confirmed yet.";
    public static final String INVALID_DESIRED_DATE = "Appointment desired date should be between two weeks before start or end date.";
    public static final String INVALID_DESIRED_DATE_STRING = "Desired Date is not valid.";
    public static final String INVALID_EXPIRED_ACCESS_TOKEN = "Access Token is invalid/expired. Please login again to refresh the access token.";
    public static final String INVALID_ISSUE_LENGTH = "Issue can be maximum 200 characters long.";


    public static final String AUTHORIZATION_FAILED_USER_ROLE_NOT_ALLOWED = "This user's role is not allowed to perform this action.";

    public  static final String UNAUTHORIZED_USER_FOR_CONTRACT_FOR_APPOINTMENT_CREATION = "This user is not authorized to create Appointment with this Contract ID.";

    public static final String INVALID_CONTRACTORS_USER_ID = "No User found with the specified Contractor's User ID.";
    public static final String INVALID_CONTRACTORS_USER_ROLE = "The user with Contractor's User ID is not a resident user.";
}
