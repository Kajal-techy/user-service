package com.userservice.service;

import com.userservice.dao.UserDao;
import com.userservice.exception.UserExistsException;
import com.userservice.exception.UserNotFoundException;
import com.userservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserDao userDao;

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
    public User createUser(User user) throws UserExistsException {
        logger.info("Entering UserServiceImpl.createUser with parameter user {}.", user.toString());
        User existingUser = userDao.findUserByUserName(user.getUserName());
        if (existingUser == null) {
            return userDao.saveUser(user);
        } else
            throw new UserExistsException("User already exists with userName = " + existingUser.getUserName() );
    }

    /**
     * This function is calling dao for getting the user details by Id
     *
     * @param id
     * @return User
     */
    @Override
    public User findUserById(String id) {
        logger.info("Entering UserServiceImpl.findUserById with parameter id {}.", id);
        Optional<User> user = userDao.findUserById(id);
        if (user.isPresent())
            return user.get();
        else
            throw new UserNotFoundException("User does not exist with id = " + id);
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
        logger.info("Entering UserServiceImpl.findUserByUserNameAndPassword with parameters userName {} and password {}.", userName, passsword);
        Optional<User> user = userDao.findUserByUserNameAndPassword(userName, passsword);
        if (user.isPresent())
            return user;
        else
            throw new UserNotFoundException("User does not exist with userName =" + userName);
    }
}
