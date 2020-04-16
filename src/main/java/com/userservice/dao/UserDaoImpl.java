package com.userservice.dao;

import com.userservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public Optional<User> findUserByUserNameAndPassword(String userName, String password) {
        return defaultUserDao.findByUserNameAndPassword(userName, password);
    }

    /**
     * This function will check the user existence in the database by username
     *
     * @param userName
     * @return User
     */
    @Override
    public Optional<User> findUserByUserName(String userName) {
        return defaultUserDao.findByUserName(userName);
    }
}
