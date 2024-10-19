package com.brokerage_agency_system.controller;

import com.brokerage_agency_system.model.User;
import com.brokerage_agency_system.DTO.UserCreateTO;
import com.brokerage_agency_system.DTO.UserTO;
import com.brokerage_agency_system.service.UserService;
import com.brokerage_agency_system.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserValidator validator;
    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateTO userCreateTO) {
        try {
            validator.validateForCreate(userCreateTO);
            User createdUser = userService.saveUser(userCreateTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserTO userTO) {
        try {
            var validatedUser = validator.validateForUpdate(username, userTO);
            User updatedUser = userService.updateUser(userTO, validatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (NullPointerException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            var existingUser = validator.validateForDelete(userId);
            userService.deleteUser(existingUser);
            return ResponseEntity.accepted().body("Deleted user with id: " + userId);
        } catch (NullPointerException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
