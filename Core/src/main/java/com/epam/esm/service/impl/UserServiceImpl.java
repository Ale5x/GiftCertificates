package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.model.ERole;
import com.epam.esm.model.Role;
import com.epam.esm.model.UserStatus;
import com.epam.esm.service.RoleService;
import com.epam.esm.service.UserStatusService;
import com.epam.esm.util.LocalUtil;
import com.epam.esm.model.Dto.UserDto;
import com.epam.esm.model.User;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.ValidatorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The type User service implements methods of the User interface.
 * The class is annotated as a service, which qualifies it to be automatically created by component-scanning.
 *
 * @author Alexander Pishchala
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String USER_EXIST_CODE = "code.user.exist_error";
    private static final String USER_BY_ID_NOT_FOUND_CODE = "code.user.not_found";
    private static final String USER_BY_ID_NOT_FOUND = "User not found by ID. ID: ";
    private static final String USER_BY_EMAIL_EXIST_CODE = "code.user.email.exist";
    private static final String USER_INVALID_DATA = "code.user.invalid.data";
    private static final String REGEX_DELIMITER = "/";

    private final static String ISO_FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss";

    private UserDao userDao;
    private LocalUtil localUtil;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;
    private UserStatusService userStatusService;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           LocalUtil localUtil,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService,
                           UserStatusService userStatusService) {
        this.userDao = userDao;
        this.localUtil = localUtil;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userStatusService = userStatusService;
    }

    @Override
    public boolean save(UserDto userDto) {
        ValidatorEntity.user(userDto, localUtil);
        Optional<User> existUser = userDao.findUserByEmail(userDto.getEmail());
        if (existUser.isPresent()) {
            throw new ValidatorException(localUtil.getMessage(USER_EXIST_CODE));
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setCreateDate(getLocalDateTime());
        user.setUpdateDate(getLocalDateTime());

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRole(ERole.USER.name()));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userDao.create(user);
    }

    @Transactional
    @Override
    public Optional<UserDto> showUser(int id) {
        Optional<User> user = userDao.findUser(id);
        return user.map(value -> builderUserDto(Collections.singletonList(value)).get(0));
    }

    @Transactional
    @Override
    public Optional<UserDto> showUserByEmail(String email) {
        Optional<User> user = userDao.findUserByEmail(email);
        if (user.isPresent()) {
            UserDto newUser = user.map(value -> builderUserDto(Collections.singletonList(value)).get(0)).get();
            newUser.setPassword(user.get().getPassword());
            return Optional.of(newUser);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public List<UserDto> showUserList(int limit, int offset) {
        List<User> userList = userDao.findUserList(limit, offset);
        return builderUserDto(userList);
    }

    @Transactional
    @Override
    public List<UserDto> showUsersByName(String name, int limit, int offset) {
        List<User> userList = userDao.findUsersByName(name, limit, offset);
        return builderUserDto(userList);
    }

    @Override
    public int showCountUsers() {
        return userDao.findCountUsers();
    }

    @Override
    public boolean changeUserRole(UserDto userDto) {
        Optional<User> user = userDao.findUser(userDto.getUserId());
        if (user.isEmpty()) {
            logger.error(USER_BY_ID_NOT_FOUND + userDto.getUserId());
            throw new ServiceException((localUtil.getMessage(USER_BY_ID_NOT_FOUND_CODE)) + userDto.getUserId());
        }
        Role roleDB = roleService.findRole(userDto.getRoles().stream().findFirst().orElseThrow(() -> new ServiceException((localUtil.getMessage(USER_BY_ID_NOT_FOUND_CODE)) + userDto.getUserId())));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleDB);

        user.get().setRoles(roleSet);
        user.get().setUpdateDate(getLocalDateTime());
        return userDao.update(user.get());
    }

    @Override
    public boolean changeUserStatus(UserDto userDto) {
        Optional<User> user = userDao.findUser(userDto.getUserId());
        if (user.isEmpty()) {
            logger.error(USER_BY_ID_NOT_FOUND + userDto.getUserId());
            throw new ServiceException((localUtil.getMessage(USER_BY_ID_NOT_FOUND_CODE)) + userDto.getUserId());
        }
        UserStatus status = userStatusService.findStatus(userDto.getStatus());
        user.get().setStatus(status);
        user.get().setUpdateDate(getLocalDateTime());
        return userDao.update(user.get());
    }

    @Override
    public boolean update(UserDto userDto) {
        User user = userDao.findUser(userDto.getUserId()).orElseThrow(() -> new ServiceException((localUtil.getMessage(USER_BY_ID_NOT_FOUND_CODE)) + userDto.getUserId()));
        ValidatorEntity.userForUpdate(userDto, localUtil);
        List<String> listPasswords = Arrays.asList(userDto.getPassword().split(REGEX_DELIMITER));

        if(!listPasswords.isEmpty() && passwordEncoder.matches(listPasswords.get(0), user.getPassword())) {
            user.setFirstName(userDto.getFirstName() != "" ? userDto.getFirstName() : user.getFirstName());
            user.setLastName(userDto.getLastName() != "" ? userDto.getLastName() : user.getLastName());
            user.setUpdateDate(getLocalDateTime());

            if(userDao.findUserByEmail(userDto.getEmail()).isEmpty()) {
                user.setEmail(userDto.getEmail() != "" ? userDto.getEmail() : user.getEmail());
            } else {
                throw new ServiceException((localUtil.getMessage(USER_BY_EMAIL_EXIST_CODE)));
            }

            if(listPasswords.size() == 2) {
                user.setPassword(passwordEncoder.encode(listPasswords.get(1).trim()));
            }
        } else {
            throw new ServiceException((localUtil.getMessage(USER_INVALID_DATA)));
        }
        return true;
    }

    @Override
    public boolean remove(int id) {
        User user = userDao.findUser(id).orElseThrow(() -> new ServiceException((localUtil.getMessage(USER_BY_ID_NOT_FOUND_CODE)) + id));
        return userDao.delete(user);
    }

    private LocalDateTime getLocalDateTime() {
        return LocalDateTime.parse(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(ISO_FORMAT_DATE)));
    }

    /**
     * Builder list of User Dto by list of User entity. This is a helper method needed to avoid
     * code duplication.
     *
     * @param userList the list of users.
     *
     * @return list of User Dto.
     */
    @Transactional
    private List<UserDto> builderUserDto(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setRoles(mapRoles(user.getRoles()));
            userDto.setStatus(user.getStatus().getName().name());
            userDto.setCreateDate(user.getCreateDate());
            userDto.setLastUpdateDate(user.getUpdateDate());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    /**
     * The method maps a collection of roles from a Set list
     * @param roles the set list of roles.
     * @return Collection roles.
     */
    private Collection<String> mapRoles(Set<Role> roles) {
        Collection<String> role = new ArrayList<>();
        roles.forEach(eRole -> role.add(eRole.getName().name()));
        return role;
    }
}
