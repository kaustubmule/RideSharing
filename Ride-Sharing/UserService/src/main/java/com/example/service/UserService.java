package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public String getUserRoleById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return (user != null) ? user.getRole() : null;
    }


    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        String email = user.getEmail();
        if (email != null && !email.isEmpty()) {
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent()) {
                throw new RuntimeException("User with email already exists");
            }
        } else {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Hash the password before saving
        String rawPassword = user.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
    }
}
