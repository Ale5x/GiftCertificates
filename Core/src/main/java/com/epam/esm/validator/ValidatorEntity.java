package com.epam.esm.validator;

import com.epam.esm.model.Dto.GiftCertificateDto;
import com.epam.esm.model.Dto.UserDto;
import com.epam.esm.util.LocalUtil;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

/**
 * The ValidatorEntity class for validating entities data.
 *
 * @author Alexander Pishchala
 */
public class ValidatorEntity {

    /**
     * Validation Constants.
     */
    private static final String REGEX_EMAIL = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
    private static final int MAX_LENGTH_CERTIFICATE_STRING_DATA = 120;
    private static final int MAX_LENGTH_USER_STRING_DATA = 120;
    private static final int MAX_LENGTH_CERTIFICATE_DESCRIPTION_STRING_DATA = 120;
    private static final int MAX_LENGTH_TAG_STRING_DATA = 120;
    private static final double MAX_VALUE_PRICE = 99999.99;
    private static final double MIN_VALUE_PRICE = 0.0;
    private static final int MAX_DAYS_DURATION = 365;
    private static final int MIN_DAYS_DURATION = 1;

    /**
     * Validation messages that occur when an error occurs.
     *
     */
    private static final String CERTIFICATE = "code.certificate";
    private static final String CERTIFICATE_FOR_UPDATE = "code.certificate.for.update";
    private static final String CERTIFICATE_NAME = "code.certificate.name";
    private static final String CERTIFICATE_PRICE = "code.certificate.price";
    private static final String CERTIFICATE_DURATION = "code.certificate.duration";
    private static final String CERTIFICATE_DESCRIPTION = "code.certificate.description";
    private static final String CERTIFICATE_IS_EMPTY = "code.certificate.is.empty";
    private static final String NAME_VALUE_IS_EMPTY = "code.certificate.name.is.empty";
    private static final String DESCRIPTION_VALUE_IS_EMPTY = "code.certificate.description.is.empty";
    private static final String VALUE_IS_EMPTY = "code.value.is.empty";
    private static final String VALUE_IS_NOT_VALID = "code.value.is.not.valid";
    private static final String DURATION_VALUE_IS_NOT_VALID = "code.certificate.duration.is.not.valid";
    private static final String NAME_IS_TOO_LONG = "code.certificate.name.is.long";
    private static final String DESCRIPTION_IS_TOO_LONG = "code.certificate.description.is.long";
    private static final String TAG = "code.tag";
    private static final String TAG_IS_EMPTY = "code.tag.is.empty";
    private static final String TAG_NAME_IS_TOO_LONG = "code.tag.name.is.long";
    private static final String USER = "code.user";
    private static final String USER_IS_EMPTY = "code.user.is.empty";
    private static final String USER_FIRST_NAME_IS_EMPTY = "code.user.firstname.is.empty";
    private static final String USER_LAST_NAME_IS_EMPTY = "code.user.lastname.is.empty";
    private static final String USER_EMAIL_IS_EMPTY = "code.user.email.is.empty";
    private static final String USER_FIRST_NAME_IS_LONG = "code.user.firstname.is.long";
    private static final String USER_LAST_NAME_IS_LONG = "code.user.lastname.is.long";
    private static final String USER_EMAIL_IS_LONG = "code.user.email.is.long";
    private static final String USER_EMAIL_IS_NOT_VALID = "code.user.email.is.not.valid";
    private static final String USER_PASSWORD_IS_NOT_VALID = "code.user.password.is.not.valid";

    /**
     * The method for certificate validation.
     *
     */
    public static boolean certificate(GiftCertificateDto certificate, LocalUtil localUtil) throws ValidatorException {
        if (certificate == null) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE), localUtil.getMessage(CERTIFICATE_IS_EMPTY));
        }
        if (certificate.getName() == null) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_NAME), localUtil.getMessage(NAME_VALUE_IS_EMPTY));
        }
        if (certificate.getDescription() == null) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_DESCRIPTION),
                                         localUtil.getMessage(DESCRIPTION_VALUE_IS_EMPTY));
        }
        if (certificate.getPrice() == null) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_PRICE),
                                         localUtil.getMessage(VALUE_IS_EMPTY));
        }
        if (certificate.getName().length() > MAX_LENGTH_CERTIFICATE_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_NAME),
                                         localUtil.getMessage(NAME_IS_TOO_LONG));
        }

        if (certificate.getDescription().length() > MAX_LENGTH_CERTIFICATE_DESCRIPTION_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_DESCRIPTION),
                                         localUtil.getMessage(DESCRIPTION_IS_TOO_LONG));
        }
        if (certificate.getPrice().doubleValue() <= MIN_VALUE_PRICE ||
            certificate.getPrice().doubleValue() > MAX_VALUE_PRICE) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_PRICE),
                                         localUtil.getMessage(VALUE_IS_NOT_VALID) + certificate.getPrice());
        }
        if (!(certificate.getDuration() >= MIN_DAYS_DURATION &&
            certificate.getDuration() < MAX_DAYS_DURATION)) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_DURATION),
                                    localUtil.getMessage(DURATION_VALUE_IS_NOT_VALID) + certificate.getDuration());
        }
        return true;
    }

    /**
     * The method for certificate validation for update.
     *
     */
    public static boolean certificateForUpdate(GiftCertificate certificate, LocalUtil localUtil) throws ValidatorException {
        if (certificate == null) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_FOR_UPDATE),
                                         localUtil.getMessage(CERTIFICATE_IS_EMPTY));
        }
        if (certificate.getName() != null && certificate.getName().length() > MAX_LENGTH_CERTIFICATE_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_NAME),
                                         localUtil.getMessage(NAME_IS_TOO_LONG));
        }
        if (certificate.getDescription() != null && certificate.getDescription().length() > MAX_LENGTH_CERTIFICATE_DESCRIPTION_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_DESCRIPTION),
                                         localUtil.getMessage(DESCRIPTION_IS_TOO_LONG));
        }
        if (certificate.getPrice() != null && (certificate.getPrice().doubleValue() <= MIN_VALUE_PRICE ||
                certificate.getPrice().doubleValue() > MAX_VALUE_PRICE)) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_PRICE),
                                         localUtil.getMessage(VALUE_IS_NOT_VALID) + certificate.getPrice());
        }
        if (certificate.getDuration() != 0 && (certificate.getDuration() <= MIN_DAYS_DURATION &&
                certificate.getDuration() > MAX_DAYS_DURATION)) {
            throw new ValidatorException(localUtil.getMessage(CERTIFICATE_DURATION),
                                         localUtil.getMessage(DURATION_VALUE_IS_NOT_VALID) + certificate.getDuration());
        }
        return true;
    }

    /**
     * The method for tag validation.
     *
     */
    public static boolean tag(Tag tag, LocalUtil localUtil) {
        if (tag == null) {
            throw new ValidatorException(localUtil.getMessage(TAG), localUtil.getMessage(TAG_IS_EMPTY));
        }
        if (tag.getName().length() > MAX_LENGTH_TAG_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(TAG),
                                         localUtil.getMessage(TAG_NAME_IS_TOO_LONG) + tag.getName());
        }
        return true;
    }

    /**
     * The method for user validation.
     *
     */
    public static boolean user(UserDto user, LocalUtil localUtil) {
        if (user == null) {
            throw new ValidatorException(localUtil.getMessage(USER), localUtil.getMessage(USER_IS_EMPTY));
        }
        if (user.getFirstName() == null) {
            throw new ValidatorException(localUtil.getMessage(USER), localUtil.getMessage(USER_FIRST_NAME_IS_EMPTY));
        }
        if (user.getLastName() == null) {
            throw new ValidatorException(localUtil.getMessage(USER), localUtil.getMessage(USER_LAST_NAME_IS_EMPTY));
        }
        if (user.getEmail() == null) {
            throw new ValidatorException(localUtil.getMessage(USER), localUtil.getMessage(USER_EMAIL_IS_EMPTY));
        }
        if (user.getFirstName().length() > MAX_LENGTH_USER_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(USER),
                                         localUtil.getMessage(USER_FIRST_NAME_IS_LONG) + user.getFirstName());
        }
        if (user.getLastName().length() > MAX_LENGTH_USER_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(USER),
                                         localUtil.getMessage(USER_LAST_NAME_IS_LONG) + user.getLastName());
        }
        if (user.getEmail().length() > MAX_LENGTH_USER_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(USER),
                                         localUtil.getMessage(USER_EMAIL_IS_LONG) + user.getEmail());
        }
        if (!user.getEmail().matches(REGEX_EMAIL)) {
            throw new ValidatorException(localUtil.getMessage(USER),
                                         localUtil.getMessage(USER_EMAIL_IS_NOT_VALID) +user.getEmail());
        }
        return true;
    }

    public static boolean userForUpdate(UserDto user, LocalUtil localUtil) {
        if (user == null) {
            throw new ValidatorException(localUtil.getMessage(USER), localUtil.getMessage(USER_IS_EMPTY));
        }
        if (user.getFirstName().length() > MAX_LENGTH_USER_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(USER),
                    localUtil.getMessage(USER_FIRST_NAME_IS_LONG) + user.getFirstName());
        }
        if (user.getLastName().length() > MAX_LENGTH_USER_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(USER),
                    localUtil.getMessage(USER_LAST_NAME_IS_LONG) + user.getLastName());
        }
        if (user.getEmail().length() > MAX_LENGTH_USER_STRING_DATA) {
            throw new ValidatorException(localUtil.getMessage(USER),
                    localUtil.getMessage(USER_EMAIL_IS_LONG) + user.getEmail());
        }
        if (!user.getEmail().matches(REGEX_EMAIL)) {
            throw new ValidatorException(localUtil.getMessage(USER),
                    localUtil.getMessage(USER_EMAIL_IS_NOT_VALID) +user.getEmail());
        }
        return true;
    }
}
