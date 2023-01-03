package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

/**
 * The class {@code ReportException} to create a response to the user due to which the error occurred, by generating an
 * error message response and HTTP status.
 *
 * @author Alexander Pishchala
 */
public class ReportException {

    private final String message;
    private final HttpStatus httpStatus;

    /**
     * Instantiates a new ReportException.
     *
     * @param message the message.
     * @param httpStatus the HTTP status.
     */
    public ReportException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Getting the message.
     *
     * @return the error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getting Http status.
     *
     * @return the HTTP status.
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}