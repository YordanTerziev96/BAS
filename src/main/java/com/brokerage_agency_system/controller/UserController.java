package com.brokerage_agency_system.controller;

import com.brokerage_agency_system.DTO.UserDTO;
import com.brokerage_agency_system.model.User;
import com.brokerage_agency_system.DTO.UserCreateTO;
import com.brokerage_agency_system.DTO.UserTO;
import com.brokerage_agency_system.service.UserService;
import com.brokerage_agency_system.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "http://localhost:4200")
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
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateTO userCreateTO) {
        validator.validateForCreate(userCreateTO);
        User createdUser = userService.saveUser(userCreateTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UserTO userTO) {
        var validatedUser = validator.validateForUpdate(id, userTO);
        User updatedUser = userService.updateUser(userTO, validatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {

        var existingUser = validator.validateForDelete(userId);
        userService.deleteUser(existingUser);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Deleted user with id: " + userId);
        return ResponseEntity.accepted().body(response);
    }
}
