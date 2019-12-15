package org.contract.common;

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
    public static final String REQUIRED_CONTRACTORS_USER_ID = "Contractor's User ID is required.";
    public static final String REQUIRED_ROOM_NUMBER = "Room Number is required.";
    public static final String REQUIRED_END_DATE = "Start Date is required.";
    public static final String REQUIRED_START_DATE = "End Date is required.";
    public static final String REQUIRED_STATUS = "Valid Status is required.";
    public static final String REQUIRED_OPERATION = "Operation is required.";

    public static final String INVALID_PAGE_NUM = "Invalid 'pageNum' value.";
    public static final String INVALID_PAGE_SIZE = "Invalid 'pageSize' value.";
    public static final String INVALID_EMAIL = "Valid Email is required.";
    public static final String INVALID_PHONE = "Valid Phone Number is required.";
    public static final String INVALID_END_DATE = "Valid End Date is required.";
    public static final String INVALID_ROOM_NUMBER = "Valid Room Number is required.";
    public static final String INVALID_CONTRACTORS_USER_ID = "No User found with the specified User ID.";
    public static final String INVALID_CONTRACTORS_USER_ROLE = "Contractor is not a resident.";


    public static final String AUTHORIZATION_FAILED_USER_ROLE_NOT_ALLOWED = "This user's role is not allowed to perform this action.";

    public static final String PAGINATION_RANGE_EXCEEDS = "Pagination attributes exceeds data range.";
}
