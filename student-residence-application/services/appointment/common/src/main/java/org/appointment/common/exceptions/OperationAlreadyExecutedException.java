package org.appointment.common.exceptions;

public class OperationAlreadyExecutedException extends Exception {
    public OperationAlreadyExecutedException(String message) {
        super(message);
    }
}
