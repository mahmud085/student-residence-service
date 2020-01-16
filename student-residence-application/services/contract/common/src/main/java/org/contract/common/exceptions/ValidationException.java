package org.contract.common.exceptions;

/**
 * Exception for validation failure
 *
 * @author Mohammed Mostakim Ornob
 *
 */
public class ValidationException extends Exception {
    /**
     * Constructs ValidationException.
     * @param message Exception message.
     */
    public ValidationException(String message) {
        super(message);
    }
}
