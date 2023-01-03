package com.epam.esm.config;

import com.epam.esm.controller.PathPageConstant;
import com.epam.esm.filter.JwtConfiguration;
import com.epam.esm.filter.JwtTokenProvider;
import com.epam.esm.model.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.springframework.http.HttpMethod.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String STARS = "**";

    private JwtTokenProvider jwtTokenProvider;

    @Qualifier("authUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(pathBuilder(PathPageConstant.LOGIN),
                        pathBuilder(PathPageConstant.USER_CREATE), pathBuilder(PathPageConstant.USER_EXIST)).permitAll()

                .antMatchers(GET,
                        pathBuilder(PathPageConstant.SECTION_CERTIFICATE),
                        pathBuilder(PathPageConstant.SECTION_TAG)).permitAll()

//
                .antMatchers(POST, pathBuilder(PathPageConstant.ORDER_CREATE)).hasAuthority(ERole.USER.name())
                .antMatchers(GET, pathBuilder(PathPageConstant.ORDER_USER)).hasAuthority(ERole.USER.name())
                .antMatchers(DELETE, pathBuilder(PathPageConstant.ORDER_DELETE)).hasAnyAuthority(ERole.USER.name(), ERole.ADMIN.name())

                .antMatchers(GET, pathBuilder(PathPageConstant.USER_GET_USERNAME)).hasAnyAuthority(ERole.USER.name(), ERole.ADMIN.name())

                .antMatchers(POST, pathBuilder(PathPageConstant.SECTION_USER), pathBuilder(PathPageConstant.SECTION_CERTIFICATE), pathBuilder(PathPageConstant.SECTION_TAG), pathBuilder(PathPageConstant.SECTION_ORDER)).hasAuthority(ERole.ADMIN.name())
                .antMatchers(GET, pathBuilder(PathPageConstant.SECTION_TAG), pathBuilder(PathPageConstant.SECTION_USER), "store/admin/**").hasAuthority(ERole.ADMIN.name())
                .antMatchers(DELETE, pathBuilder(PathPageConstant.SECTION_TAG), pathBuilder(PathPageConstant.SECTION_CERTIFICATE), pathBuilder(PathPageConstant.SECTION_USER), pathBuilder(PathPageConstant.SECTION_USER)).hasAuthority(ERole.ADMIN.name())

//                .antMatchers(POST, "store/admin/**").hasRole(ERole.ADMIN.name())


                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfiguration(jwtTokenProvider));
    }

    private String pathBuilder(String path) {
        StringBuilder builder = new StringBuilder();
        builder.append(PathPageConstant.FLASH).append(path).append(PathPageConstant.FLASH).append(STARS);
        return builder.toString();
    }


//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors().and()
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                .and()
//                .authorizeRequests()
//                .antMatchers(pathBuilder(PathPageConstant.LOGIN),
//                        pathBuilder(PathPageConstant.USER_CREATE), pathBuilder(PathPageConstant.USER_EXIST)).permitAll()
//
//                .antMatchers(GET,
//                        pathBuilder(PathPageConstant.SECTION_CERTIFICATE),
//                        pathBuilder(PathPageConstant.SECTION_TAG)).permitAll()
//                .antMatchers(GET, "store/admin/**").hasAuthority(ERole.ADMIN.name())
//                .antMatchers(POST, "store/admin/**").hasAuthority(ERole.ADMIN.name())
//
//                .anyRequest().authenticated()
//                .and()
//                .apply(new JwtConfiguration(jwtTokenProvider));
//    }

}
