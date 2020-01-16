package org.contract.common.exceptions;

/**
 * throws Validation Exceptions
 *
 */

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
