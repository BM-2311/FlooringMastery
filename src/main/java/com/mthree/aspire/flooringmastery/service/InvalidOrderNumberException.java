package com.mthree.aspire.flooringmastery.service;

/**
 *
 * @author barin
 */
public class InvalidOrderNumberException extends Exception {

    public InvalidOrderNumberException(String message) {
        super(message);
    }

    public InvalidOrderNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
