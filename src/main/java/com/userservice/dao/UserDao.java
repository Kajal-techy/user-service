package com.userservice.dao;

import com.userservice.model.User;

import java.util.Optional;

public interface UserDao {

    public User saveUser(User user);

    public Optional<User> findUserById(String id);

    public Optional<User> findUserByUserNameAndPassword(String userName, String password);

    public Optional<User> findUserByUserName(String userName);
}