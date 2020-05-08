package com.userservice.service;

import com.userservice.exception.NotFoundException;
import com.userservice.exception.UserExistsException;
import com.userservice.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user) throws UserExistsException;

    User findUserById(String s, String id) throws NotFoundException;

    List<User> getUsers(String userName, String loggedInUserId) throws NotFoundException;
}