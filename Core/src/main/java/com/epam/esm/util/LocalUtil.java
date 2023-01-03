package com.epam.esm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * The type LocalUtil profile configuration is a configuration java class.
 *
 * @author Alexander Pishchala
 */
@Component
public class LocalUtil {

    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;

    /**
     * Receiving a message by code according to the locale.
     *
     * @param code the code of message.
     *
     * @return the message according to locale
     */
    public String getMessage(String code) {
        return resourceBundleMessageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
