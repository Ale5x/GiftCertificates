package com.epam.esm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Password encryption algorithm class.
 *
 * @author Alexander Pishchala
 */

@Configuration
public class PasswordEncoderConfiguration {

    /**
     * Method passwordEncoder returns BCryptPasswordEncoder bean
     * @return BCryptPasswordEncoder entity
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}