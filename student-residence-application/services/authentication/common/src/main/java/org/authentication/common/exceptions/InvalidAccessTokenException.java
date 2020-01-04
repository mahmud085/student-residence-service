package org.authentication.common.exceptions;

public class InvalidAccessTokenException extends Exception {
    public InvalidAccessTokenException(String message) {
        super(message);
    }
}
