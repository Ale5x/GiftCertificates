package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.model.*;
import com.epam.esm.model.Dto.UserDto;
import com.epam.esm.service.UserStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDaoImpl userDao;
    @Mock
    private RoleServiceImpl roleService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserStatusService userStatusService;

    private UserDto userDto = null;
    private UserStatus userStatus = new UserStatus(1, EUserStatus.ACTIVE);
    private User user = new User();
    private List<User> userList = new ArrayList<>();

    private int limit = 10;
    private int offset = 0;

    @BeforeEach
    void setUp() {
        user.setUserId(1);
        user.setEmail("email@gmail.com");
        user.setFirstName("first name");
        user.setLastName("last name");
        user.setPassword("password");
        user.setStatus(userStatus);


        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, ERole.USER));
//        roles.add(new Role(2, ERole.ADMIN));
        user.setRoles(roles);

        userDto = new UserDto("First name", "Last name", "Email@gmail.com");

        for (int i = 0; i < 5; i++) {
            userList.add(user);
        }
    }

    @Test
    void save() {
        when(userDao.create(any(User.class))).thenReturn(true);
        boolean condition = userService.save(userDto);

        assertTrue(condition);
    }

    @Test
    void remove() {
        int id = 1;
        given(userDao.findUser(id)).willReturn(Optional.of(user));
        when(userDao.delete(any(User.class))).thenReturn(true);
        boolean condition = userService.remove(id);

        assertTrue(condition);
    }

    @Test
    void showUserByIdRightTest() {
        int id = 1;
        given(userDao.findUser(id)).willReturn(Optional.of(user));
        Optional<UserDto> user = userService.showUser(id);
        assertTrue(user.isPresent());
    }

    @Test
    void showUserByIdWrongTest() {
        int id = 11;
        given(userDao.findUser(id)).willReturn(Optional.empty());
        Optional<UserDto> user = userService.showUser(id);
        assertTrue(user.isEmpty());
    }

    @Test
    void showUserByEmailRightTest() {
        String email = "test@email.com";
        given(userDao.findUserByEmail(email)).willReturn(Optional.of(user));
        Optional<UserDto> user = userService.showUserByEmail(email);
        assertTrue(user.isPresent());
    }

    @Test
    void showUserByEmailWrongTest() {
        String email = "test@email.com";
        given(userDao.findUserByEmail(email)).willReturn(Optional.empty());
        Optional<UserDto> user = userService.showUserByEmail(email);
        assertTrue(user.isEmpty());
    }


    @Test
    void showUserList() {
        given(userDao.findUserList(limit, offset)).willReturn(userList);
        List<UserDto> userList = userService.showUserList(limit, offset);
        assertFalse(userList.isEmpty());
        assertTrue(userList.size() > 0);
    }

    @Test
    void showCountUsers() {
        given(userDao.findCountUsers()).willReturn(5);
        int countRows = userService.showCountUsers();
        assertTrue(countRows > 0);
    }

//    @Test
//    void updateStatusRightTest() {
//        given(userDao.update(user)).willReturn(true);
//        given(userStatusService.findStatus(userStatus.getName().name())).willReturn(userStatus);
//        assertTrue(userService.changeUserStatus(userDto));
//    }

//    @Test
//    void changeUserRoleRightTest() {
//        int userId = 1;
//        given(userDao.findUser(userId)).willReturn(Optional.of(user));
//        given(roleService.findRole(ERole.ADMIN.name())).willReturn(new Role(2, ERole.ADMIN));
//        when(userDao.update(any(User.class))).thenReturn(true);
//
//        String role = ERole.ADMIN.name().toLowerCase();
//        boolean condition = userService.changeUserRole(role, userId);
//        assertTrue(condition);
//    }


}