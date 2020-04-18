package com.userservice.dao;

import com.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    public User saveUser(User user);

    public Optional<User> findUserById(String id);

    public User findUserByUserNameAndPassword(String userName, String password);

    public User findUserByUserName(String userName);

    public List<User> findAllUsers();
}