package com.epam.esm.dao.impl;

import com.epam.esm.model.*;
import com.epam.esm.configuration.DevelopmentConfig;
import com.epam.esm.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfig.class)
@ActiveProfiles("development")
class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    private int limit = 10;
    private int offset = 0;

    @Test
    void create() {
        int startPosition = userDao.findCountUsers();
        User user = new User("Alexander", "G", "alexander_g@email.com", "password", new UserStatus(1, EUserStatus.ACTIVE));
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2, ERole.ADMIN));
        user.setRoles(roles);

        boolean condition = userDao.create(user);
        int finishPosition = userDao.findCountUsers();
        List<User> users = userDao.findUserList(limit, offset);

        assertTrue(condition);
        assertTrue(finishPosition > startPosition);
    }

    @Test
    void findUser() {
        int userId = 1;
        Optional<User> user = userDao.findUser(userId);
        assertTrue(user.isPresent());
    }

    @Test
    void update() {
        int userId = 1;
        Optional<User> userBeforeOptional = userDao.findUser(userId);
        User userBeforeUodater = userBeforeOptional.get();
        Set<Role> roles = userBeforeUodater.getRoles();
        Role role = new Role(2, ERole.ADMIN);
        roles.add(role);
        userBeforeUodater.setRoles(roles);

        userDao.update(userBeforeUodater);
        Optional<User> userAfterUpdateOptional = userDao.findUser(userId);
        User userAfterUpdate = userAfterUpdateOptional.get();
        Set<Role> rolesAfterUpdate = userAfterUpdate.getRoles();
        assertTrue(rolesAfterUpdate.stream().anyMatch(name -> ERole.ADMIN.name().equalsIgnoreCase(name.getName().name())));
    }

    @Test
    void delete() {
        int startPosition = userDao.findCountUsers();
        User user = new User();
        user.setUserId(1);

        boolean condition = userDao.delete(user);
        int finishPosition = userDao.findCountUsers();
        assertTrue(condition);
        assertTrue(finishPosition < startPosition);
    }

    @Test
    void getUserList() {
        List<User> users = userDao.findUserList(limit, offset);

        assertTrue(users.size() > 0);
        assertFalse(users.isEmpty());
    }
}