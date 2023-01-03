package com.epam.esm.controller;

import org.springframework.stereotype.Component;

/**
 * The constant class for working with controllers. This describes the constant variables received in the request.
 *
 * @author Alexander Pishchala
 */
@Component
public class ConstantController {

    public final static String METHOD_GET = "GET";
    public final static String PREVIOUS_LINK = "Previous";
    public final static String NEXT_LINK = "Next";
    public final static String PAGE = "page";
    public final static String SIZE = "size";
    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String PRICE = "price";
    public final static String DURATION = "duration";
    public final static String ROLE = "role";
    public final static String EMAIL = "email";

    private ConstantController() {
    }
}
