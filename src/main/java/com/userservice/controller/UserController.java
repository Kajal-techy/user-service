package com.userservice.controller;

import com.userservice.exception.ForbiddenRequest;
import com.userservice.exception.UserExistsException;
import com.userservice.model.User;
import com.userservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ApiOperation(value = "enter product details", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully aaded"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user) throws UserExistsException {
        log.info("Entering UserController.createUser with parameter user {}.", user.toString());
        User createdUser = userService.createUser(user);
        log.info("Created user with userId {}", createdUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * It will return the user details and will perform the search by Id
     *
     * @param id
     * @param loggedInUserId
     * @return ResponseEntity<User>
     */
    @ApiOperation(value = "Get user details by Id", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Details will come"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
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
    @ApiOperation(value = "get User Details by userName", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Details will come"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(required = false) String userName, HttpServletRequest request) {
        log.info("Entering UserController.getUsers with parameters userName {}", userName);
        return ResponseEntity.ok().body(userService.getUsers(userName, (String) request.getAttribute("id")));
    }
}
