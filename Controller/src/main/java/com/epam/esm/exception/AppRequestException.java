package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

/**
 * The class {@code AppRequestException} is subclass of {@code RuntimeException}. Class represent an exception
 * that may occur on a WEB application comes into existence at runtime.
 *
 * @author Alexander Pishchala
 */
public class AppRequestException extends RuntimeException {

    private HttpStatus status;

    /**
     * Instantiates a new Gift certificate.
     *
     * @param message the message.
     */
    public AppRequestException(String message) {
        super(message);
    }


    /**
     * Instantiates a new Gift certificate.
     *
     * @param message the message.
     * @param status the HTTP status.
     */
    public AppRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Getting Http status.
     *
     * @return the HTTP status.
     */
    public HttpStatus getStatus() {
        return status;
    }
}
