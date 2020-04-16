package com.userservice.controller;

import com.userservice.exception.UserExistsException;
import com.userservice.model.User;
import com.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/v1")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * It is making a post call for user creation
     *
     * @param user
     * @return ResponseEntity<User>
     * @throws UserExistsException
     */
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user) throws UserExistsException {
        log.info("Entering UserController.createUser with parameter user {}.", user.toString());
        return ResponseEntity.ok().body(userService.createUser(user));
    }

    /**
     * It will return the user details and will perform the search by Id
     *
     * @param id
     * @return ResponseEntity<User>
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserDetailsById(@PathVariable String id) {
        log.info("Entering UserController.getUserDetailsById with parameter id {}.", id);
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
    public ResponseEntity<User> getUserByUserNameAndPassword(@RequestParam String userName, @RequestParam(required = false) String password) {
        log.info("Entering UserController.getUserByUserNameAndPassword with parameters userName {} ", userName);
        return ResponseEntity.ok().body(userService.findUserByUserNameAndPassword(userName, password).get());
    }
}
