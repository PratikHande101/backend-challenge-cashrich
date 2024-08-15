package com.pratik.cash_rich_assignment.assignment.service;

import com.pratik.cash_rich_assignment.assignment.model.User;
import com.pratik.cash_rich_assignment.assignment.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    // Constructor injection
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        // Checking if username or email already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()
                || userRepository.findByEmail(user.getEmail()).isPresent()
        ) {
            // throwing exception as user does not exist
            throw new IllegalArgumentException("Username or email already in use.");
        }

        // setting the encrypted password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Saving the user
        return userRepository.save(user);
    }


    @Override
    public User updateUser(User user) {
        // validating email and username
        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> userByUsername = userRepository.findByUsername(user.getUsername());

        if (userByEmail.isPresent() && !userByEmail.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Email already in use");
        }

        if (userByUsername.isPresent() && !userByUsername.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Username already in use.");
        }

        // save updated user
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
