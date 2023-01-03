package com.epam.esm.dao;

import com.epam.esm.model.ERole;
import com.epam.esm.model.Role;

import java.util.Optional;

/**
 * Interface {@link RoleDao} provides operation with data of database table 'roles'.
 *
 * @author Alexander Pishchala
 */
public interface RoleDao {

    /**
     * The method returns specified Order by id.
     *
     * @param role the ERole name.
     *
     * @return specified Optional Role by name.
     */
    Optional<Role> findRole(ERole role);
}
