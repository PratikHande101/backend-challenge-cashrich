package com.pratik.cash_rich_assignment.assignment.service;

import com.pratik.cash_rich_assignment.assignment.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    User updateUser(User user);
    Optional<User> findUserByUsername(String username);
}
