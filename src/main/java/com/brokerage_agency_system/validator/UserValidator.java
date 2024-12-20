package com.brokerage_agency_system.validator;

import com.brokerage_agency_system.model.User;
import com.brokerage_agency_system.DTO.UserCreateTO;
import com.brokerage_agency_system.DTO.UserTO;
import com.brokerage_agency_system.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserValidator {

    private UserRepository repository;

    public void validateForCreate(UserCreateTO createTO) {
        if (createTO == null) {
            throw new NullPointerException("Empty object");
        }
        Optional<User> existingUser = repository.findByUsername(createTO.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }
        if (!createTO.getPassword().equals(createTO.getRepeatedPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
    }

    public User validateForUpdate(@NotNull String userId, @NotNull UserTO userTO) {
        if (userId.isBlank() || userTO == null) {
            throw new NullPointerException("Empty object");
        }
        var existingUser = repository.findById(Long.valueOf(userId));
        if (existingUser.isEmpty()) {
            throw new NoSuchElementException("There is no such user with id: " + userId);
        }
        return existingUser.get();
    }

    public User validateForDelete(Long userId) {
        if (userId == null) {
            throw new NullPointerException("Empty object");
        }
        var existingUser = repository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new NoSuchElementException("There is no such user with username: " + userId);
        }
        return existingUser.get();
    }
}
