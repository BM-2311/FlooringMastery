package com.mthree.aspire.flooringmastery.service;

/**
 *
 * @author barin
 */
public class InvalidStateException extends Exception {

    public InvalidStateException(String message) {
        super(message);
    }

    public InvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }

}
