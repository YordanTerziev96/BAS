package com.brokerage_agency_system.service;

import com.brokerage_agency_system.model.User;
import com.brokerage_agency_system.DTO.UserCreateTO;
import com.brokerage_agency_system.DTO.UserTO;
import com.brokerage_agency_system.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(UserCreateTO userTO) {

        var encodedPassword = Base64.getEncoder().encodeToString(userTO.getPassword().getBytes());
        var user = User.builder()
                .username(userTO.getUsername())
                .password(encodedPassword)
                .phone(userTO.getPhone())
                .email(userTO.getEmail()).build();
        return userRepository.save(user);
    }

    public User updateUser(UserTO userTO, User user) {
        user.setDescription(userTO.getDescription());
        user.setEmail(userTO.getEmail());
        user.setPhone(userTO.getPhone());

        return userRepository.save(user);
    }

    public void deleteUser(User userToDelete) {
        userRepository.delete(userToDelete);
    }
}
