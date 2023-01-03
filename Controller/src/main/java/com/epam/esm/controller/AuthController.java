package com.epam.esm.controller;

import com.epam.esm.filter.JwtTokenProvider;
import com.epam.esm.model.Dto.UserDto;
import com.epam.esm.security.AuthUserDetailsService;
import com.epam.esm.security.ConstantSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * User authentication controller.
 *
 * @author Alexander Pishchala
 */
@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private AuthUserDetailsService authUserDetailsService;
    @Qualifier("passwordEncoder")
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, AuthUserDetailsService authUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authUserDetailsService = authUserDetailsService;
    }

    /**
     * User authentication method.
     *
     * @param userDto data of user like email and password.
     * @return If authorization is successful, it will return OK.
     * @throws IOException
     */
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody UserDto userDto) throws IOException {
        UserDetails userDetails = authUserDetailsService.loadUserByUsername(userDto.getEmail());
       if(passwordEncoder.matches(userDto.getPassword(), userDetails.getPassword())) {
           Map<Object, Object> responseMap = addHeaders(userDetails);
           return new ResponseEntity<>(responseMap,HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * The method adds data to the header for the response.
     *
     * @param userDetails is data of the user
     * @return Map data.
     */
    protected Map<Object, Object> addHeaders(UserDetails userDetails) {

        UserDto userDto = mapUser((User) userDetails);
        String token = jwtTokenProvider.createToken(userDto);
        Map<Object, Object> headers = new HashMap<>();

        headers.put(ConstantSecurity.ACCESS_TOKEN, token);
        headers.put(ConstantSecurity.ROLES, userDetails.getAuthorities().toString());
        headers.put(ConstantSecurity.EMAIL, userDto.getEmail());

        return headers;
    }

    /**
     * Method for forming a User with UserDetails to add data to the header.
     * @param userSpringS is data of UserDetails from spring
     * @return userDto
     */
    private UserDto mapUser(User userSpringS) {
        UserDto userDto = new UserDto();
        userDto.setEmail(userSpringS.getUsername());
        Collection<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : userSpringS.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
        userDto.setRoles(roles);
        return userDto;
    }
}
