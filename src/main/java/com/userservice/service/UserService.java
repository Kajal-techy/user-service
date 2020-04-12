package com.userservice.service;

import com.userservice.exception.UserExistsException;
import com.userservice.model.User;

import java.util.Optional;

public interface UserService {

    public User saveUser(User user) throws UserExistsException;

    public User findUserById(String id);

    public Optional<User> findUserByUserNameAndPassword(String userName, String password);
}