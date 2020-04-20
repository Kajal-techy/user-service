package com.userservice;

import com.userservice.dao.UserDao;
import com.userservice.exception.NotFoundException;
import com.userservice.exception.UserExistsException;
import com.userservice.model.Address;
import com.userservice.model.User;
import com.userservice.service.UserServiceImpl;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplUnitTests {

    User user, otherUser;

    @Mock
    private UserDao userDaoMock;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Before
    public void preRequisiteTestData() {

        Address address = new Address(1, 12, "Parliament", "Toronto", "Ontario", "Canada", 123456);
        user = new User("1", "DemoTest", "UnitTest", "JUNIT", "123456", address);
        otherUser = new User("2", "Ram", "Sharma", "TestUser1", "1234567", address);
    }

    @Test
    public void createUserWithSuccess() {
        when(userDaoMock.findUserByUserName(user.getUserName())).thenThrow(NotFoundException.class);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        when(userDaoMock.saveUser(user)).thenReturn(user);
        assertEquals(userServiceImpl.createUser(user), user);
    }

    @Test(expected = UserExistsException.class)
    public void createUserAndThrowErrorIfUserExists() {
        when(userDaoMock.findUserByUserName(user.getUserName())).thenReturn(user);
        when(userDaoMock.saveUser(user)).thenThrow(UserExistsException.class);
        userServiceImpl.createUser(user);
    }

    @Test
    public void createUserAndVerifyExistsErrorMessage() {
        Exception exception = null;
        when(userDaoMock.findUserByUserName(user.getUserName())).thenReturn(user);
        try {
            userServiceImpl.createUser(user);
        } catch (Exception ex) {
            exception = ex;
        }
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof UserExistsException);
        assertEquals(exception.getMessage(), "User already exists with userName = JUNIT");
    }

    @Test
    public void findUserByIdWithSuccess() {
        when(userDaoMock.findUserById(user.getId())).thenReturn(Optional.of(user));
        User fetchedUser = userServiceImpl.findUserById(user.getId(), user.getId());
        Assert.assertEquals(fetchedUser, user);
    }

    @Test
    public void findUserByIdIfLoggedInUserIdIsDifferent() {
        when(userDaoMock.findUserById(otherUser.getId())).thenReturn(Optional.of(otherUser));
        User fetchedUser = userServiceImpl.findUserById(otherUser.getId(), user.getId());
        otherUser.setAddress(null);
        otherUser.setPassword("**REDACTED**");
        Assert.assertEquals(fetchedUser, otherUser);
    }

    @Test
    public void findUserByIdAndThrowErrorIfUserNotExists() {
        Exception exception = null;
        String nonExistId = "12";
        when(userDaoMock.findUserById(nonExistId)).thenReturn(Optional.empty());
        try {
            userServiceImpl.findUserById(nonExistId, user.getId());
        } catch (Exception ex) {
            exception = ex;
        }
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof NotFoundException);
        assertEquals("User does not exist with id = " + nonExistId, exception.getMessage());
    }

    @Test
    public void getUsersIfLoggedInUserIdIsSameAndSearchByUserName() {
        when(userDaoMock.findUserByUserName(user.getUserName())).thenReturn(user);
        List<User> fetchedUsers = userServiceImpl.getUsers(user.getUserName(), user.getId());
        Assert.assertNotNull(fetchedUsers);
        Assert.assertEquals(1, fetchedUsers.size());
        Assert.assertEquals(fetchedUsers, List.of(user));
    }

    @Test
    public void getUsersIfLoggedInUserIdIsDiffAndSearchByUserName() {
        when(userDaoMock.findUserByUserName(user.getUserName())).thenReturn(user);
        List<User> fetchedUsers = userServiceImpl.getUsers(user.getUserName(), otherUser.getId());
        Assert.assertNotNull(fetchedUsers);
        User userClone = (User) user.clone();
        userClone.setAddress(null);
        userClone.setPassword("**REDACTED**");
        Assert.assertEquals(fetchedUsers, List.of(userClone));
    }

    @Test
    public void getUsersIfLoggedInUserIdIsNullAndSearchByUserName() {
        when(userDaoMock.findUserByUserName(user.getUserName())).thenReturn(user);
        List<User> fetchedUsers = userServiceImpl.getUsers(user.getUserName(), null);
        Assert.assertNotNull(fetchedUsers);
        User userClone = (User) user.clone();
        Assert.assertEquals(fetchedUsers, List.of(userClone));
    }

    @Test
    public void getUsersIfLoggedInUserIdIsSameAndSearchByAll() {
        when(userDaoMock.findAllUsers()).thenReturn(List.of(user));
        List<User> fetchedUsers = userServiceImpl.getUsers(null, user.getId());
        Assert.assertNotNull(fetchedUsers);
        Assert.assertEquals(fetchedUsers, List.of(user));
    }

    @Test
    public void getUsersIfLoggedInUserIdIsDiffAndSearchByAll() {
        when(userDaoMock.findAllUsers()).thenReturn(List.of(user));
        List<User> fetchedUsers = userServiceImpl.getUsers(null, otherUser.getId());
        User userClone = (User) user.clone();
        Assert.assertNotNull(fetchedUsers);
        userClone.setAddress(null);
        userClone.setPassword("**REDACTED**");
        Assert.assertArrayEquals(fetchedUsers.toArray(), Arrays.array(userClone));
    }

    @Test
    public void getUsersAndThrowErrorIfUserNotFoundByUserName() {
        Exception exception = null;
        when(userDaoMock.findUserByUserName(otherUser.getUserName())).thenThrow(new NotFoundException("User does not exist with userName = " + otherUser.getUserName()));
        try {
            userServiceImpl.getUsers(otherUser.getUserName(), user.getId());
        } catch (Exception ex) {
            exception = ex;
        }
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof NotFoundException);
        assertEquals("User does not exist with userName = " + otherUser.getUserName(), exception.getMessage());
    }

    @Test
    public void getUsersAndReturnEmptyListIfNoUserExists() {
        when(userDaoMock.findAllUsers()).thenReturn(List.of());
        List<User> fetchedUsers = userServiceImpl.getUsers(null, user.getId());
        Assert.assertNotNull(fetchedUsers);
        Assert.assertEquals(0, fetchedUsers.size());
        Assert.assertEquals(fetchedUsers, List.of());
    }
}
