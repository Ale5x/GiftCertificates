package com.epam.esm.service;

import com.epam.esm.model.Role;

/**
 * The interface Role service contains methods for business logic with role.
 *
 * @author Alexander Pishchala
 */
public interface RoleService {

    /**
     * Show Role by name.
     *
     * @param role the ERole name.
     *
     * @return the specified Role by name.
     */
    Role findRole(String role);
}
