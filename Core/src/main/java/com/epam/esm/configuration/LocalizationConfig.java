package com.epam.esm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

/**
 * The type Local configuration is a configuration java class. Used to set the locale for messages.
 *
 * @author Alexander Pishchala
 */
@Configuration
@ComponentScan(basePackages = "com.epam.esm")
public class LocalizationConfig implements WebMvcConfigurer {

    /**
     * Bean to set configuration for ResourceBundleMessage, including encoding.
     *
     * @return ResourceBundleMessageSource.
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("locale");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        return resourceBundleMessageSource;
    }

    /**
     * Bean to set configuration for LocaleResolver. Locale setting. The default locale is English.
     *
     * @return LocaleResolver.
     */
    @Bean
    public LocaleResolver localeResolver() {
        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(Arrays.asList(new Locale("ru"), new Locale("en")));
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }
}
