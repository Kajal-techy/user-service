package com.userservice.service;

import com.userservice.dao.UserDao;
import com.userservice.exception.NotFoundException;
import com.userservice.exception.UserExistsException;
import com.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * This function is calling Dao and saving user details
     *
     * @param user
     * @return User
     * @throws UserExistsException
     */
    @Override
    public User createUser(User user) throws UserExistsException {
        log.info("Entering UserServiceImpl.createUser with parameter user {}", user.toString());
        User existingUser = null;
        try {
            existingUser = userDao.findUserByUserName(user.getUserName());
        } catch (NotFoundException exception) {
            log.info("UserServiceImpl.createUser exception : {} encountered", exception.getMessage());
        }
        if (existingUser == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userDao.saveUser(user);
        }
        throw new UserExistsException("User already exists with userName = " + existingUser.getUserName());
    }

    /**
     * This function is calling dao for getting the user details by Id
     *
     * @param id
     * @return User
     */
    @Override
    public User findUserById(String id, String loggedInUserId) {
        log.info("Entering UserServiceImpl.findUserById with parameter id {}", id);
        Optional<User> user = userDao.findUserById(id);
        if (user.isPresent()) {
            List<User> users = this.hideOtherUsersData(List.of(user.get()), loggedInUserId);
            return (users.get(0));
        }
        throw new NotFoundException("User does not exist with id = " + id);
    }

    /**
     * This function is calling dao for getting the user details by username and password
     *
     * @param userName
     * @return Optional<User>
     */
    @Override
    public List<User> getUsers(String userName, String loggedInUserId) {
        log.info("Entering UserServiceImpl.getUsers with parameters userName {}", userName);
        List<User> users;
        if ((userName != null)) {
            users = List.of(userDao.findUserByUserName(userName));
            loggedInUserId = users.get(0).getId();
        } else {
            users = userDao.findAllUsers();
        }
        return this.hideOtherUsersData(users, loggedInUserId);
    }

    public List<User> hideOtherUsersData(List<User> users, String loggedInUserId) {
        users.forEach(user -> {
            if (!user.getId().equals(loggedInUserId)) {
                user.setPassword("**REDACTED**");
                user.setAddress(null);
            } });
        return users;
    }
}
