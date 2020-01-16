package org.contract.common;

/**
 * Messages to show in response.
 *
 */

public class Messages {
    public static final String INTERNAL_ERROR = "An internal error occurred.";
    public static final String CONTRACT_ID_REQUIRED = "Contract ID is required.";
    public static final String REQUEST_BODY_REQUIRED = "Request body is required.";
    public static final String CONTRACT_NOT_FOUND_WITH_ID = "No contract found with the specified Contract ID.";
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
    public static final String INVALID_CONTRACTORS_USER_ID = "No User found with the specified Contractor's User ID.";
    public static final String INVALID_CONTRACTORS_USER_ROLE = "The user with Contractor's User ID is not a resident user.";
    public static final String INVALID_EXPIRED_ACCESS_TOKEN = "Access Token is invalid/expired. Please login again to refresh the access token.";

    public static final String CONTRACT_CREATION_START_DATE_IN_PAST = "The contract cannot start on past date.";
    public static final String CONTRACT_CREATION_START_DATE_TOO_EARLY = "The contract must start after the confirmation period (2 weeks after the creation) ends.";
    public static final String CONTRACT_CREATION_CONTRACT_ALREADY_EXIST_FOR_ROOM = "The specified room already has a Confirmed/Pending contract for the specified dates.";
    public static final String CONTRACT_CONFIRMATION_WINDOW_EXPIRED = "A contract can only be confirmed within the 2 weeks of it's creation.";
    public static final String CONTRACT_EXTENSION_NOT_YET_CONFIRMED = "An unconfirmed contract cannot be extended.";
    public static final String CONTRACT_EXTENSION_INVALID_OPERATION_DATE = "A contract can only be extended before 3 months of it's current end date.";
    public static final String CONTRACT_EXTENSION_INVALID_EXTENSION_PERIOD = "A contract can only be extended for 6 months.";
    public static final String CONTRACT_TERMINATION_NOT_YET_CONFIRMED = "An unconfirmed contract cannot be terminated.";
    public static final String CONTRACT_TERMINATION_INVALID_OPERATION_DATE = "A contract can only be terminated before 3 months of the new end date.";

    public static final String AUTHORIZATION_FAILED_USER_ROLE_NOT_ALLOWED = "This user's role is not allowed to perform this action.";

    public static final String CONTRACT_ALREADY_CONFIRMED = "Contract is already confirmed";
}
