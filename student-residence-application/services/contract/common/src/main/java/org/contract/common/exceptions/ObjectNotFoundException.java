package org.contract.common.exceptions;

/**
 * throws Object Not Found Exceptions
 *
 * @author Mahmud
 *
 */

public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
