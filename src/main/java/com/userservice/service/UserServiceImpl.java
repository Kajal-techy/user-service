package com.userservice.service;

import com.userservice.dao.UserDao;
import com.userservice.exception.NotFoundException;
import com.userservice.exception.UserExistsException;
import com.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        log.info("Entering UserServiceImpl.createUser with parameter user {}.", user.toString());
        Optional<User> existingUser = userDao.findUserByUserName(user.getUserName());
        if (!existingUser.isPresent()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userDao.saveUser(user);
        } else
            throw new UserExistsException("User already exists with userName = " + existingUser.get().getUserName());
    }

    /**
     * This function is calling dao for getting the user details by Id
     *
     * @param id
     * @return User
     */
    @Override
    public User findUserById(String id) {
        log.info("Entering UserServiceImpl.findUserById with parameter id {}.", id);
        Optional<User> user = userDao.findUserById(id);
        if (user.isPresent())
            return user.get();
        else
            throw new NotFoundException("User does not exist with id = " + id);
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
        log.info("Entering UserServiceImpl.findUserByUserNameAndPassword with parameters userName {} and password.", userName);
        Optional<User> user;
        if ((userName != null) && (passsword != null)) {
            user = userDao.findUserByUserNameAndPassword(userName, passsword);
            if (user.isPresent())
                return user;
        } else if ((userName != null) && (passsword == null)) {
            user = userDao.findUserByUserName(userName);
            if (user.isPresent())
                return user;
        }
        throw new NotFoundException("User does not exist with userName = " + userName);
    }
}
