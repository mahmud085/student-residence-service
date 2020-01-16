package org.contract.common.exceptions;

/**
 * Exception for invalid operation
 *
 * @author Mohammed Mostakim Ornob
 *
 */
public class InvalidOperationException extends Exception {
    /**
     * Constructs InvalidOperationException.
     * @param message Exception message.
     */
    public InvalidOperationException(String message) {
        super(message);
    }
}
