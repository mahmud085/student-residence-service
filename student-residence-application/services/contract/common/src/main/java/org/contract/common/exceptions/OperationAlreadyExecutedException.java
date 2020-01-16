package org.contract.common.exceptions;

/**
 * throws Operation Already Executed Exceptions
 *
 * @author Mahmud
 *
 */

public class OperationAlreadyExecutedException extends Exception {
    public OperationAlreadyExecutedException(String message) {
        super(message);
    }
}
