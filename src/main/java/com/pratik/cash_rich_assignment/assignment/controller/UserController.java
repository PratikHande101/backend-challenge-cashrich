package com.pratik.cash_rich_assignment.assignment.controller;

import com.pratik.cash_rich_assignment.assignment.dto.UserDto;
import com.pratik.cash_rich_assignment.assignment.dto.UserResponseDto;
import com.pratik.cash_rich_assignment.assignment.model.User;
import com.pratik.cash_rich_assignment.assignment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserDto userDto) {
        User user = userService.registerUser(
          User.builder()
                  .firstName(userDto.getFirstName())
                  .lastName(userDto.getLastName())
                  .email(userDto.getEmail())
                  .username(userDto.getUsername())
                  .password(userDto.getPassword())
                  .mobile(userDto.getMobile())
                  .build()
        );

        UserResponseDto response = UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody UserDto userDto) {
        return userService.loginUser(userDto.getUsername(), userDto.getPassword())
                .map(user -> new ResponseEntity<>(
                    UserResponseDto.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .build()
                        , HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
