package com.userservice.service;

import com.userservice.dao.UserDao;
import com.userservice.exception.UserExistsException;
import com.userservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * This function is calling Dao and saving user details
     *
     * @param user
     * @return User
     * @throws UserExistsException
     */
    @Override
    public User saveUser(User user) throws UserExistsException {
        logger.info("saveUser(user) function execution has started");
        User userNew = userDao.findUserByUserName(user.getUserName());
        if (userNew == null) {
            userNew = userDao.saveUser(user);
            return userNew;
        } else
            throw new UserExistsException("User already exists");
    }

    /**
     * This function is calling dao for getting the user details by Id
     *
     * @param id
     * @return User
     */
    @Override
    public User findUserById(String id) {
        logger.info("findUserById(id) function execution has started");
        Optional<User> user = userDao.findUserById(id);
        if (user.isPresent())
            return userDao.findUserById(id).get();
        else
            throw new UserExistsException("User already exists");
    }

    /**
     * This function is calling dao for getting the user details by username and password
     *
     * @param userName
     * @param passsword
     * @return Optional<User>
     */
    @Override
    public Optional<User> findUserByUserNameAndPassword(String userName, String passsword) {
        logger.info("findUserByUserNameAndPassword(userName, password) function execution has started");
        Optional<User> user = userDao.findUserByUserNameAndPassword(userName, passsword);
        if (user.isPresent())
            return user;
        else
            throw new UserExistsException("User already exists");
    }
}
