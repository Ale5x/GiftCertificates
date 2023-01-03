package com.epam.esm.service.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.ERole;
import com.epam.esm.model.Role;
import com.epam.esm.service.RoleService;
import com.epam.esm.util.LocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type Role service implements methods of the Role interface.
 * The class is annotated as a service, which qualifies it to be automatically created by component-scanning.
 *
 * @author Alexander Pishchala
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    private RoleDao roleDao;
    private LocalUtil localUtil;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao, LocalUtil localUtil) {
        this.roleDao = roleDao;
        this.localUtil = localUtil;
    }

    private static final String ROLE_NOT_FOUND_CODE = "code.role.not_found";
    private static final String ROLE_NOT_FOUND = "Unknown role. Role: ";
    private static final String ROLE_NOT_FOUND_IN_DATABASE = "The role is not in the database. Role: ";


    @Override
    public Role findRole(String role) {
        Optional<Role> roleOptional = roleDao.findRole(checkRole(role));
        if (roleOptional.isEmpty()) {
            logger.error(ROLE_NOT_FOUND_IN_DATABASE + role);
        }
        return roleOptional.orElseThrow(() -> new ServiceException(localUtil.getMessage(ROLE_NOT_FOUND_CODE) + role));
    }

    /**
     * The method checks if the role exists in the system.
     * @param role the ERole type name.
     * @return ERole type.
     */
    private ERole checkRole(String role) {
        if (!(role.equalsIgnoreCase(ERole.USER.name())) && !(role.equalsIgnoreCase(ERole.ADMIN.name()))) {
            logger.error(ROLE_NOT_FOUND + role);
            throw new ServiceException((localUtil.getMessage(ROLE_NOT_FOUND_CODE) + role));
        }
        return ERole.valueOf(role.toUpperCase());
    }
}
