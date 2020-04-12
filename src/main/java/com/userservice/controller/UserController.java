package com.userservice.controller;

import com.userservice.exception.UserExistsException;
import com.userservice.model.User;
import com.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * This function is making a post call for user creation
     *
     * @param user
     * @return ResponseEntity<User>
     * @throws UserExistsException
     */
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user) throws UserExistsException {
        logger.info("CreateUser(user) function execution has started");
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    /**
     * It will return the user details and will perform the search by Id
     *
     * @param id
     * @return ResponseEntity<User>
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserDetailsById(@PathVariable String id) {
        logger.info("getUserDetailsById(id) function execution has started");
        return ResponseEntity.ok().body(userService.findUserById(id));
    }

    /**
     * It will return the user details and perform search by userName and Password
     *
     * @param userName
     * @param password
     * @return ResponseEntity<User>
     */
    @GetMapping("/users")
    public ResponseEntity<User> getUserByUserNameAndPassword(@RequestParam String userName, @RequestParam String password) {
        logger.info("getUserByUserNameAndPassword(userName, password) function execution has started");
        return ResponseEntity.ok().body(userService.findUserByUserNameAndPassword(userName, password).get());
    }
}
