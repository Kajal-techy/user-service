package com.userservice.dao;

import com.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User saveUser(User user);

    Optional<User> findUserById(String id);

    User findUserByUserNameAndPassword(String userName, String password);

    User findUserByUserName(String userName);

    List<User> findAllUsers();
}