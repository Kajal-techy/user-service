package com.userservice.service;

import com.userservice.dao.UserDao;
import com.userservice.exception.UserExistsException;
import com.userservice.exception.UserNotFoundException;
import com.userservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        logger.debug("Entering UserServiceImpl.createUser with parameter user {}.", user.toString());
        Optional<User> existingUser = userDao.findUserByUserName(user.getUserName());
        if (!existingUser.isPresent()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userDao.saveUser(user);
        } else
            throw new UserExistsException("User already exists with userName = " + existingUser.get().getUserName() );
    }

    /**
     * This function is calling dao for getting the user details by Id
     *
     * @param id
     * @return User
     */
    @Override
    public User findUserById(String id) {
        logger.debug("Entering UserServiceImpl.findUserById with parameter id {}.", id);
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
        logger.debug("Entering UserServiceImpl.findUserByUserNameAndPassword with parameters userName {} and password {}.", userName, passsword);
        Optional<User> user;
        if ((userName != null) && (passsword != null)) {
            user = userDao.findUserByUserNameAndPassword(userName, passsword);
             if (user.isPresent())
                return user;
             else
                 throw new UserNotFoundException("User does not exist with userName =" + userName);
        }
        else if ((userName != null) && (passsword == null)) {
            {
                user = userDao.findUserByUserName(userName);
                if (user.isPresent())
                    return user;
                else
                    throw new UserNotFoundException("User does not exist with userName =" + userName);
            }
        }
        else
            throw new UserNotFoundException("User does not exist with userName =" + userName);
    }
}
