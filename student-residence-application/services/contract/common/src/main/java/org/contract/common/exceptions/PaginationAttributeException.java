package org.contract.common.exceptions;

/**
 * Exception for invalid pagination attribute
 *
 * @author Mohammed Mostakim Ornob
 *
 */
public class PaginationAttributeException extends Exception {
    /**
     * Constructs PaginationAttributeException.
     * @param message Exception message.
     */
    public PaginationAttributeException(String message) {
        super(message);
    }
}
