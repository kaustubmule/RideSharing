package com.example.user.service.service;

import com.example.user.service.entity.User;

import java.util.List;

public interface UserService {
    //create a user
    User saveUser(User user);

    //get all users
    List<User> getAllUser();

    //get single user using userid
    User getUser(String userId);

    //delete user
    void deleteUser(String userId);

    //update user
    User updateUser(String userId, User user);

    List<User> getAllDriversByRole(String driver);
}
