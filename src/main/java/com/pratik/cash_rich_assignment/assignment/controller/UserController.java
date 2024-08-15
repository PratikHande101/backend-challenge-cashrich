package com.pratik.cash_rich_assignment.assignment.controller;

import com.pratik.cash_rich_assignment.assignment.dto.UserDto;
import com.pratik.cash_rich_assignment.assignment.dto.UserResponseDto;
import com.pratik.cash_rich_assignment.assignment.model.CustomUserDetails;
import com.pratik.cash_rich_assignment.assignment.model.User;
import com.pratik.cash_rich_assignment.assignment.service.CoinmarketService;
import com.pratik.cash_rich_assignment.assignment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CoinmarketService coinmarketService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, CoinmarketService coinmarketService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.coinmarketService = coinmarketService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> registerUser(
            @RequestHeader("X-Api-Origin") String apiOriginHeader,
            @RequestBody UserDto userDto) {
        String expectedHeaderVal = "cashrich-predefined-header";

        if (!expectedHeaderVal.equals(apiOriginHeader)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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
    public ResponseEntity<?> loginUser(
            @RequestHeader("X-Api-Origin") String apiOriginHeader) {
        String expectedHeaderVal = "cashrich-predefined-header";

        if (!expectedHeaderVal.equals(apiOriginHeader)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok("User logged in successfully");
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserDto userDto) {
        // fetching the currently authenticated user from security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        // making sure that user is only updating their profile
        if (!loggedInUsername.equals(userDto.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Fetching the existing users entity from the database
        Optional<User> existingUserOptional = userService.findUserByUsername(loggedInUsername);
        if (existingUserOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the user details
        User updatedUser = existingUserOptional.get();
        updatedUser.setFirstName(userDto.getFirstName());
        updatedUser.setLastName(userDto.getLastName());
        updatedUser.setEmail(userDto.getEmail());
        updatedUser.setMobile(userDto.getMobile());

        // Updating the password if it is provided by the client
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            updatedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Save updated user
        User savedUser = userService.updateUser(updatedUser);

        UserResponseDto response = UserResponseDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/coin-data")
    public ResponseEntity<String> getCoinData() {
        // getting the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        // fetching the coin data from the service
        String coinData = coinmarketService.getCoinData(userId);

        return ResponseEntity.ok(coinData);
    }

}
