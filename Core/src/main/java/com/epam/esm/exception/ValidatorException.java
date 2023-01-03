package com.epam.esm.exception;

/**
 * The class {@code ValidatorException} is subclass of {@code RuntimeException}. Class represent an exception
 * that may occur on a Service layer of the application occur during data validation
 *
 * @author Alexander Pishchala
 */
public class ValidatorException extends RuntimeException {

    private static final long serialVersionUID = 5032327162368123507L;

    private String name;
    private String typeError;

    /**
     * Instantiates a new ValidatorException.
     *
     * @param name the name.
     */
    public ValidatorException(String name) {
        super(name);
        this.name = name;
    }

    /**
     * Instantiates a new ValidatorException.
     *
     * @param name the name.
     * @param typeError the type error.
     */
    public ValidatorException(String name, String typeError) {
        super(String.format("%s is not valid. Message: %s", name, typeError));
        this.name = name;
        this.typeError = typeError;
    }

    /**
     * Getting name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getting type error.
     *
     * @return the type error.
     */
    public String getTypeError() {
        return typeError;
    }
}
