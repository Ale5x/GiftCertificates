package com.epam.esm.exception;

import org.springframework.stereotype.Component;

/**
 * The class contains error message codes to send messages to the user, given the locale.
 *
 * @author Alexander Pishchala
 */
@Component
public final class ErrorMessage {

    private ErrorMessage() {}

    public static final String INVALID_PAGE_FORMAT = "code.page.is.not.number";
    public static final String INVALID_SIZE_FORMAT = "code.size.is.not.number";
    public static final String INVALID_ID_FORMAT = "code.invalid.id.format";
    public static final String CERTIFICATE_NOT_FOUND = "code.certificate.not_found";
    public static final String ORDER_BY_ID_NOT_FOUND = "code.order.not_found";
    public static final String TAG_BY_ID_NOT_FOUND = "code.tag.not_found";
    public static final String TAG_BY_NAME_NOT_FOUND = "code.tag.not_found.by.name";
    public static final String TAG_NOT_FOUND = "code.tag.not.found";
    public static final String USER_BY_ID_NOT_FOUND = "code.user.not_found";
    public static final String USER_BY_EMAIL_NOT_FOUND = "code.user.not_found_email";
    public static final String INVALID_PRICE_FORMAT = "code.invalid.price";
    public static final String INVALID_NUMBER_FORMAT = "code.invalid.value";
    public static final String USER_NOT_AUTHORISATION = "code.error.authorization";
    public static final String TOKEN_MISSING = "code.token.missing";
    public static final String TOKEN_INVALID = "code.token.invalid";
}
