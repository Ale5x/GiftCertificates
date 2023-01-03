package com.epam.esm.security;

import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.AppRequestException;
import com.epam.esm.exception.ErrorMessage;
import com.epam.esm.model.Dto.UserDto;
import com.epam.esm.model.ERole;
import com.epam.esm.service.UserService;
import com.epam.esm.util.LocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The class {@code AuthUserDetailsService} is subclass of {@code UserDetailsService}. User authentication class.
 *
 * @author Alexander Pishchala
 */
@Component
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private LocalUtil localUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;


    /**
     * The method takes on the role of user authentication. Retrieving from the user's database by email.
     * @param email the user's email.
     * @return the (sringframework security) User's entity.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDto> user = userService.showUserByEmail(email);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException(ErrorMessage.USER_BY_EMAIL_NOT_FOUND);
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                user.get().getPassword(), mapToGrantedAuthorities(user.get().getRoles()));
    }

    public int getIdUser() {
        String userEmail = getCurrentUserEmail();
        Optional<UserDto> user = userService.showUserByEmail(userEmail);
        UserDto userFromDB = user.orElseThrow(() -> new AppRequestException(
                (localUtil.getMessage(ErrorMessage.USER_BY_EMAIL_NOT_FOUND)) + userEmail, HttpStatus.NOT_FOUND));
        return userFromDB.getUserId();
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Collection<String> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(ERole.valueOf(role.toUpperCase(Locale.ROOT)).name())
                ).collect(Collectors.toList());
    }
}
