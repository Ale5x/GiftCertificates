package com.epam.esm.exception;

/**
 * The class {@code ServiceException} is subclass of {@code RuntimeException}. The exception class throws an exception
 * if an error occurs in the service layer.
 *
 * @author Alexander Pishchala
 */
public class ServiceException extends RuntimeException {

    /**
     * Instantiates a new Gift certificate.
     */
    public ServiceException() {
    }

    /**
     * Instantiates a new Gift certificate.
     *
     * @param message the message.
     */
    public ServiceException(String message) {
        super(message);
    }


}
