package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DevelopmentConfig;
import com.epam.esm.dao.RoleDao;
import com.epam.esm.model.ERole;
import com.epam.esm.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfig.class)
@ActiveProfiles("development")
class RoleDaoImplTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    void findRole() {
        Optional<Role> role = roleDao.findRole(ERole.USER);
        assertTrue(role.isPresent());
    }
}