package org.contract.common.exceptions;

/**
 * Exception for no object found error
 *
 * @author Mohammed Mostakim Ornob
 *
 */
public class ObjectNotFoundException extends Exception {
    /**
     * Constructs ObjectNotFoundException.
     * @param message Exception message.
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
