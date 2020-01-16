package org.contract.common.exceptions;

/**
 * Exception for redundant operation
 *
 * @author Mohammed Mostakim Ornob
 *
 */
public class OperationAlreadyExecutedException extends Exception {
    /**
     * Constructs OperationAlreadyExecutedException.
     * @param message Exception message.
     */
    public OperationAlreadyExecutedException(String message) {
        super(message);
    }
}
