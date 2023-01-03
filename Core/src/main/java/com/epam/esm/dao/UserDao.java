package com.epam.esm.dao;

import com.epam.esm.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface {@link UserDao} provides operation with data of database table 'users'.
 *
 * @author Alexander Pishchala
 */
public interface UserDao {

    /**
     * The method creates new record in database table.
     *
     * @param user entity that specifies creation of new records in database table.
     *
     * @return Returns true if the operation was successful.
     */
    boolean create(User user);

    /**
     * The method returns specified User by id.
     *
     * @param id the User id.
     *
     * @return the specified Optional User by id.
     */
    Optional<User> findUser(int id);

    /**
     * The method returns specified User by email.
     *
     * @param email the User email.
     *
     * @return the specified Optional User by email.
     */
    Optional<User> findUserByEmail(String email);

    /**
     * The method will return list of Users by name.
     *
     * @param name searched user name.
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of users by name.
     */
    List<User> findUsersByName(String name, int limit, int offset);

    /**
     * The method will return list of Users.
     *
     * @param limit the number of rows to get at one time.
     * @param offset the value of the element from which the countdown starts.
     *
     * @return the specified list of users.
     */
    List<User> findUserList(int limit, int offset);

    /**
     * The method will return the number of rows in the orders table.
     *
     * @return The number of rows.
     */
    int findCountUsers();

    /**
     * The method updates the record in database table.
     *
     * @param user entity indicating the update of a record in a database table.
     *
     * @return Returns true if the operation was successful.
     */
    boolean update(User user);

    /**
     * The method removes the record in database table.
     *
     * @param user is a user identifier that specifies the identifier for deleting a record in a database table.
     *
     * @return Returns true if the operation was successful.
     */
    boolean delete(User user);
}
