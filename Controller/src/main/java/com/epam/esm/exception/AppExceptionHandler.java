package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * The class is annotated with ControllerAdvice. This is where the response is generated when errors occur.
 *
 * @author Alexander Pishchala
 */
@ControllerAdvice
public class AppExceptionHandler {

    private static final String ERROR_CODE_404 = "404";
    private static final String ERROR_IN = "Error in ";

    /**
     * The method generates an error code and status.
     *
     * @param e the AppRequestException.
     * @return the custom error
     */
    @ResponseBody
    @ExceptionHandler(value = {AppRequestException.class})
    public ResponseEntity<Object> externalError(AppRequestException e) {
        ReportException reportException = new ReportException(
                e.getMessage(),
                e.getStatus());
        return new ResponseEntity<>(reportException, e.getStatus());
    }

    /**
     * The method generates an error code and status.
     *
     * @param e the ServiceException.
     * @return the custom error
     */
    @ResponseBody
    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<Object> externalError(ServiceException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ReportException reportException = new ReportException(
                e.getMessage(),
                status);
        return new ResponseEntity<>(reportException, status);
    }

    /**
     * Not found custom error. Http status 404.
     *
     * @return the custom error
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<Object> externalError() {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ReportException reportException = new ReportException(
                ERROR_CODE_404,
                notFound);
        return new ResponseEntity<>(reportException, notFound);
    }


    /**
     * Invalid data for database error. Http status 400.
     *
     * @param e the ValidatorException.
     * @return the custom error
     */
    @ResponseBody
    @ExceptionHandler(value = {ValidatorException.class})
    public ResponseEntity<Object> externalError(ValidatorException e) {
        StringBuilder builderMessage = new StringBuilder();
        builderMessage.append(ERROR_IN).append(e.getMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ReportException reportException = new ReportException(
                builderMessage.toString(),
                badRequest);
        return new ResponseEntity<>(reportException, badRequest);
    }

    /**
     * Invalid data received in query JSON error. Http status 400.
     *
     * @param e the MethodArgumentNotValidException.
     * @return the custom error
     */
    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> externalError(MethodArgumentNotValidException e) {
        StringBuilder builderMessage = new StringBuilder();
        builderMessage.append(ERROR_IN).append(e.getBindingResult().getFieldError().getField()).append(". ")
                .append(e.getBindingResult().getFieldError().getDefaultMessage());
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ReportException reportException = new ReportException(
                builderMessage.toString(),
                badRequest);
        return new ResponseEntity<>(reportException, badRequest);
    }
}
