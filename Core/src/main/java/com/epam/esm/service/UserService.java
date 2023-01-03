package com.epam.esm.service;

import com.epam.esm.model.Dto.UserDto;

import java.util.List;
import java.util.Optional;

/**
 * The interface User service contains methods for business logic with user.
 *
 * @author Alexander Pishchala
 */
public interface UserService {

    /**
     * Create a User.
     *
     * @param userDto the entity UserDto.
     *
     * @return returns a boolean result
     */
    boolean save(UserDto userDto);

    /**
     * Show User by id.
     *
     * @param id the User id.
     *
     * @return the specified Optional User by id.
     */
    Optional<UserDto> showUser(int id);

    /**
     * Show User by email.
     *
     * @param email the User email.
     *
     * @return the specified Optional User by email.
     */
    Optional<UserDto> showUserByEmail(String email);

    /**
     * The method will return list of users.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of users.
     */
    List<UserDto> showUserList(int limit, int offset);

    /**
     * The method will return list of users by name.
     *
     * @param name searched user name.
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of users.
     */
    List<UserDto> showUsersByName(String name, int limit, int offset);

    /**
     * The method will return the number of rows in the user table.
     *
     * @return The number of rows.
     */
    int showCountUsers();

    /**
     * Adding a Role to a User.
     *
     * @param userDto the User.
     *
     * @return returns a boolean result
     */
    boolean changeUserRole(UserDto userDto);

    /**
     * Changing the user's status.
     *
     * @param userDto the User.
     *
     * @return returns a boolean result
     */
    boolean changeUserStatus(UserDto userDto);

    /**
     * Updating User Data.
     *
     * @param userDto the User.
     *
     * @return returns a boolean result
     */
    boolean update(UserDto userDto);

    /**
     * Removing user's account.
     *
     * @param id the user's id.
     *
     * @return returns a boolean result
     */
    boolean remove(int id);
    
}
