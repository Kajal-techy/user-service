package com.userservice.service;

import com.userservice.exception.NotFoundException;
import com.userservice.exception.UserExistsException;
import com.userservice.model.User;

import java.util.List;

public interface UserService {

    public User createUser(User user) throws UserExistsException;

    public User findUserById(String id, String loggedInUserId) throws NotFoundException;

    public List<User> getUsers(String userName, String loggedInUserId) throws NotFoundException;
}