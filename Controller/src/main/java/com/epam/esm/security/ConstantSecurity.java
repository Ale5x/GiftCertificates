package com.epam.esm.security;

import org.springframework.stereotype.Component;

/**
 * A class of constants for safety.
 *
 * @author Alexander Pishchala
 */
@Component
public class ConstantSecurity {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String BEARER = "Bearer";
    public static final String ROLES = "roles";
    public static final String EMAIL = "email";
    public static final String AUTHORIZATION = "Authorization";

    private ConstantSecurity() {
    }
}
