package com.example.user.service;

import com.example.user.entity.User;

import java.util.List;

public interface UserService{

    User registerUser(User user);

    String verify(User user);

    //get all users
    List<User> getAllUser();

    //get single user using userid
    User getUser(String userId);

    //delete user
    void deleteUser(String userId);

    //update user
    User updateUser(String userId, User user);

    List<User> getAllUsersByRole(String rider);

    User findByUsername(String username);
}
