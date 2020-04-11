package com.userservice.dao;

import com.userservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DefaultUserDao extends MongoRepository<User, String> {

    Optional<User> findByUserNameAndPassword(String userName, String password);

    User findByUserName(String userName);
}
