package com.userservice.service;

import com.userservice.exception.UserExistsException;
import com.userservice.exception.UserNotFoundException;
import com.userservice.model.User;

import java.util.Optional;

public interface UserService {

    public User createUser(User user) throws UserExistsException;

    public User findUserById(String id) throws UserNotFoundException;

    public Optional<User> findUserByUserNameAndPassword(String userName, String password) throws UserNotFoundException;
}