package org.authentication.common;

public class Messages {
    public static final String INTERNAL_ERROR = "An internal error occurred.";
    public static final String CONTRACT_ID_REQUIRED = "Contract ID is required.";
    public static final String REQUEST_BODY_REQUIRED = "Request body is required.";
    public static final String CONTRACT_NOT_FOUND_WITH_ID = "No contract found with the specified contract ID.";
    public static final String USER_NOT_AUTHORISED_TO_OPERATE_RESOURCE = "This User is not authorized to perform this operation on this resource.";

    public static final String SUCCESSFUL_CONFIRMATION = "Contract successfully confirmed.";
    public static final String SUCCESSFUL_EXTENSION = "Contract successfully extended.";
    public static final String SUCCESSFUL_TERMINATION = "Contract successfully terminated.";

    public static final String REQUIRED_CONTRACTORS_NAME = "Contractor's Name is required.";
    public static final String REQUIRED_CONTRACT_ID = "Contract ID is required.";
    public static final String REQUIRED_ROOM_NUMBER = "Room Number is required.";
    public static final String REQUIRED_APPOINTMENT_TYPE = "Valid Appointment Type is required";
    public static final String REQUIRED_ISSUE = "Valid Issue is required.";
    public static final String REQUIRED_APPOINTMENT_PRIORITY = "Valid Appointment Priority is required";
    public static final String REQUIRED_DESIRED_DATE = "Desired Date is required.";
    public static final String REQUIRED_APPOINTMENT_STATUS = "Valid Appointment Status is required";
    public static final String REQUIRED_USER_ID = "Valid User Id is required";
    public static final String REQUIRED_USER_EMAIL = "Valid User Email is required";
    public static final String REQUIRED_USER_PASSWORD = "Valid User Password is required";
    public static final String APPOINTMENT_NOT_FOUND="Appointment Not Found With this ID";
    public static final String APPOINTMENT_ALREADY_ACCEPTED="Appointment is already accepted";
    public static final String APPOINTMENT_DATE_EXPIRED="You need to accept appointment before desired date";
    public static final String USER_NOT_FOUND="User Not Found With this ID";
    public static final String USER_MISMATCH="User Not Found With this ID and Password";

    public static final String INVALID_PAGE_NUM = "Invalid 'pageNum' value.";
    public static final String INVALID_PAGE_SIZE = "Invalid 'pageSize' value.";
    public static final String INVALID_EMAIL = "Valid Email is required.";
    public static final String INVALID_PHONE = "Valid Phone Number is required.";
    public static final String INVALID_END_DATE = "Valid End Date is required.";
    public static final String INVALID_ROOM_NUMBER = "Valid Room Number is required.";
    public static final String INVALID_CONTRACT = "Contract is not confirmed yet";
    public static final String INVALID_DESIRED_DATE = "Appointment desired date should be between two weeks before start or end date";
    public static final String INVALID_CONTRACTORS_USER_ID = "No User found with the specified User ID.";
    public static final String INVALID_CONTRACTORS_USER_ROLE = "Contractor is not a resident.";
    public static final String INVALID_ACCESS_TOKEN = "Invalid Access Token";



    public static final String AUTHORIZATION_FAILED_USER_ROLE_NOT_ALLOWED = "This user's role is not allowed to perform this action.";
    public static final String AUTHORIZATION_FAILED_CAUSE_NO_AUTHORIZATION_HEADER = "Authorization header must be provided";

    public static final String PAGINATION_RANGE_EXCEEDS = "Pagination attributes exceeds data range.";
}
