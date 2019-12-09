package org.contract.common;

public class Messages {
    public static final String INTERNAL_ERROR = "An internal error occurred.";
    public static final String CONTRACT_NOT_FOUND_WITH_ID = "No contract found with the specified contract ID.";

    public static final String SUCCESSFUL_CONFIRMATION = "Contract successfully confirmed.";
    public static final String SUCCESSFUL_EXTENSION = "Contract successfully extended.";
    public static final String SUCCESSFUL_TERMINATION = "Contract successfully terminated.";

    public static final String REQUIRED_CONTRACTORS_NAME = "Contractors Name is required.";
    public static final String REQUIRED_CONTRACTORS_EMAIL = "Contractors Email is required.";
    public static final String REQUIRED_CONTRACTORS_PHONE = "Contractors Phone is required.";
    public static final String REQUIRED_ROOM_NUMBER = "Room Number is required.";
    public static final String REQUIRED_END_DATE = "Start Date is required.";
    public static final String REQUIRED_START_DATE = "End Date is required.";
    public static final String REQUIRED_STATUS = "Status is required.";
    public static final String REQUIRED_OPERATION = "Operation is required.";

    public static final String INVALID_PAGE_NUM = "Invalid 'pageNum' value.";
    public static final String INVALID_PAGE_SIZE = "Invalid 'pageSize' value.";
    public static final String INVALID_EMAIL = "Valid email is required.";
    public static final String INVALID_PHONE = "Valid Phone Number is required.";
    public static final String INVALID_END_DATE = "End Date is not valid.";

    public static final String CONTRACT_EXTENSION_INVALID_OPERATION_DATE = "A contract can only be extended before 3 months of it's current end date.";
    public static final String CONTRACT_EXTENSION_INVALID_EXTENSION_PERIOD = "A contract can only be extended for 6 months.";
    public static final String CONTRACT_TERMINATION_INVALID_OPERATION_DATE = "A contract can only be terminated before 3 months of it's current end date.";
}
