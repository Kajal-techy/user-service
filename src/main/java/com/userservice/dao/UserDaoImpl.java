package com.userservice.dao;

import com.userservice.exception.NotFoundException;
import com.userservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    private DefaultUserDao defaultUserDao;

    public UserDaoImpl(DefaultUserDao defaultUserDao) {
        this.defaultUserDao = defaultUserDao;
    }

    /**
     * It is saving the details in the database
     *
     * @param user
     * @return User
     */
    @Override
    public User saveUser(User user) {
        return defaultUserDao.save(user);
    }


    /**
     * This function will serach the user in the database by id
     *
     * @param id
     * @return Optional<User>
     */
    @Override
    public Optional<User> findUserById(String id) {
        log.info("Entering UserDaoImpl.findById with parameter id {}", id);
        return defaultUserDao.findById(id);
    }

    /**
     * This function will check the user existence in the database by username and password
     *
     * @param userName
     * @param password
     * @return Optional<User>
     */
    @Override
    public User findUserByUserNameAndPassword(String userName, String password) {
        log.info("Entering UserDaoImpl.findUserByUserNameAndPassword with parameters userName {}", userName);
        Optional<User> user = defaultUserDao.findByUserNameAndPassword(userName, password);
        if (user.isPresent())
            return user.get();
        throw new NotFoundException("User not found with userName =  " + userName);
    }

    /**
     * This function will check the user existence in the database by username
     *
     * @param userName
     * @return User
     */
    @Override
    public User findUserByUserName(String userName) {
        log.info("Entering UserDaoImpl.findUserByUserName with parameter userName {}", userName);
        Optional<User> user = defaultUserDao.findByUserName(userName);
        if (user.isPresent())
            return user.get();
        throw new NotFoundException("User does not exist with userName = " + userName);
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Entering UserDaoImpl.findAllUsers");
        List<User> users = defaultUserDao.findAll();
        return users;
    }
}
