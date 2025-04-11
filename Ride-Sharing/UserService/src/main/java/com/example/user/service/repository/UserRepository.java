package com.example.user.service.repository;

import com.example.user.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    List<User> findByRole(String role);
}
