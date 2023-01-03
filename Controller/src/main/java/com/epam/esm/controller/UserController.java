package com.epam.esm.controller;

import com.epam.esm.exception.AppRequestException;
import com.epam.esm.exception.ErrorMessage;
import com.epam.esm.security.AuthUserDetailsService;
import com.epam.esm.util.LocalUtil;
import com.epam.esm.model.Dto.UserDto;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.ValidatorParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * The type User Controller is a controller which operates requests from clients and generates response in
 * representational forms. Information exchanging are in a JSON forms.
 *
 * @author Alexander Pishchala
 */
@RestController
@CrossOrigin({"http://localhost:3000", "http://localhost:8080"})
public class UserController extends PaginationHelper {

    private ValidatorParams validator;
    private LocalUtil localUtil;
    private UserService userService;
    private AuthUserDetailsService authUserDetailsService;
    /**
     * Instantiates a new UserController.
     *
     * @param userService the user service
     * @param localUtil the Local.
     * @param validator the validator values.
     */
    @Autowired
    public UserController(LocalUtil localUtil,
                          UserService userService,
                          ValidatorParams validator,
                          AuthUserDetailsService authUserDetailsService) {
        this.localUtil = localUtil;
        this.userService = userService;
        this.validator = validator;
        this.authUserDetailsService = authUserDetailsService;
    }

    /**
     * Create a User.
     *
     * @param user the userDto entity.
     *
     * @return the message.
     */
    @PostMapping(value = PathPageConstant.USER_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserDto user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Getting user by id.
     *
     * @param id the user id.
     *
     * @return the user by id.
     */
    @GetMapping(value = PathPageConstant.USER_GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserDto getUser(@RequestParam(ConstantController.ID) String id) {
        validator.validId(id);
        Optional<UserDto> user = userService.showUser(Integer.parseInt(id));
        return user.orElseThrow(() -> new AppRequestException(
                (localUtil.getMessage(ErrorMessage.USER_BY_ID_NOT_FOUND)) + id, HttpStatus.NOT_FOUND));
    }

    /**
     * Getting users.
     *
     * @param page the page being viewed.
     * @param size the number of elements per page.
     *
     * @return the list of users.
     */
    @GetMapping(value = PathPageConstant.USER_GET_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UserDto> getUsers(@RequestParam(ConstantController.PAGE) String page,
                                             @RequestParam(ConstantController.SIZE) String size) {
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(UserController.class)
                .getUsers(getPreviousPage(page), size))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(UserController.class)
                .getUsers(getNextPage(page), size))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<UserDto> userDtoList = userService.showUserList(Integer.parseInt(size), getOffset(page, size));
        return CollectionModel.of(userDtoList, previousLink, nextLink);
    }

    /**
     * The method checks whether the user exists by email or not.
     *
     * @return HTTP Status Ok or Http Status Not Found.
     */
    @GetMapping(value =  PathPageConstant.USER_EXIST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> existUser(@RequestParam(ConstantController.EMAIL) String email) {
        Optional<UserDto> user = userService.showUserByEmail(email);
        System.out.println("User ---->>> " + user.toString());
        System.out.println("EMAIL ---->>> " + email);
        if (user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Getting count users.
     *
     * @return number of users.
     */
    @GetMapping(value = PathPageConstant.USER_GET_COUNT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Integer> getCountUser() {
        return new ResponseEntity<>(userService.showCountUsers(), HttpStatus.OK);
    }

    /**
     * Getting current users.
     *
     * @return The current User.
     */
    @GetMapping(value = PathPageConstant.USER_GET_USERNAME)
    public UserDto currentUserName() {
        Optional<UserDto> user = userService.showUser(getIdUserFromAuth());
        return user.orElseThrow(() -> new AppRequestException(
                (localUtil.getMessage(ErrorMessage.USER_BY_ID_NOT_FOUND)) + getIdUserFromAuth(), HttpStatus.NOT_FOUND));
    }

    /**
     * Adding a role to a user.
     *
     * @param user the user.
     * @return The HttpStatus.
     */
    @PostMapping(value = PathPageConstant.USER_CHANGE_ROLE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> changeRole(@RequestBody UserDto user) {
        if(userService.changeUserRole(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Adding a status to a user.
     *
     * @param user the user.
     * @return The HttpStatus.
     */
    @PostMapping(value = PathPageConstant.USER_CHANGE_STATUS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> changeStatus(@RequestBody UserDto user) {
        if(userService.changeUserStatus(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = PathPageConstant.SECTION_USER + "/changeData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> changeData(@RequestBody UserDto user) {
        user.setUserId(getIdUserFromAuth());
        if(userService.update(user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = PathPageConstant.SECTION_USER + "/removeAccount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> removeAccount() {
        System.out.println("REMOVING ACCOUNT....");
        if(userService.remove(authUserDetailsService.getIdUser())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = PathPageConstant.SECTION_USER + "/getUsersByName", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UserDto> getUsersByName(@RequestParam(ConstantController.PAGE) String page,
                                                     @RequestParam(ConstantController.SIZE) String size,
                                                     @RequestParam(ConstantController.NAME) String name) {
        validator.validPage(page);
        validator.validSize(size);
        Link previousLink = linkTo(methodOn(UserController.class)
                .getUsersByName(getPreviousPage(page), size, name))
                .withRel(ConstantController.PREVIOUS_LINK).withType(ConstantController.METHOD_GET);
        Link nextLink = linkTo(methodOn(UserController.class)
                .getUsersByName(getNextPage(page), size, name))
                .withRel(ConstantController.NEXT_LINK).withType(ConstantController.METHOD_GET);
        List<UserDto> userDtoList = userService.showUsersByName(name, Integer.parseInt(size),
                getOffset(page, size));

        return CollectionModel.of(userDtoList, previousLink, nextLink);
    }

//    @PostMapping(value = PathPageConstant.SECTION_USER + "/authForChangeData", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HttpStatus> authForChangedData(@RequestBody UserDto user) {
//        System.out.println("User password --->>>  " + user.getPassword());
//        if(user.getPassword().equals("123")) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }

    private int getIdUserFromAuth() {
        return authUserDetailsService.getIdUser();
    }









//    @PostMapping(value = PathPageConstant.USER_CHANGE_ROLE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<HttpStatus> changeRolePOST(@RequestParam(ConstantController.ID) String id,
//                                                 @RequestParam(ConstantController.ROLE) String role) {
//        validator.validId(id);
//        System.out.println("Changed role by post method");
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }



}
