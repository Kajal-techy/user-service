package com.userservice.controller;

import com.userservice.exception.ForbiddenRequest;
import com.userservice.exception.UserExistsException;
import com.userservice.model.User;
import com.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

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
     * @param loggedInUserId
     * @return ResponseEntity<User>
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserDetailsById(@PathVariable String id, @RequestHeader String loggedInUserId) {
        log.info("Entering UserController.getUserDetailsById with parameter id {}", id);
        if (loggedInUserId != null)
            return ResponseEntity.ok().body(userService.findUserById((id), loggedInUserId));
        throw new ForbiddenRequest("Authentication token did not have loggedInUser's Id");
    }

    /**
     * It will return the user details and perform search by userName and Password
     *
     * @param userName
     * @param request
     * @return ResponseEntity<User>
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String userName, HttpServletRequest request) {
        log.info("Entering UserController.getUsers with parameters userName {}", userName);
        return ResponseEntity.ok().body(userService.getUsers(userName, (String) request.getAttribute("id")));
    }
}
