package com.brokerage_agency_system.service;

import com.brokerage_agency_system.DTO.UserDTO;
import com.brokerage_agency_system.model.User;
import com.brokerage_agency_system.DTO.UserCreateTO;
import com.brokerage_agency_system.DTO.UserTO;
import com.brokerage_agency_system.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        List<UserDTO> userDTOS = new ArrayList<>();
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"))) {
            return users.stream()
                    .map(user -> new UserDTO(user.getFullName(), user.getImage(), user.isEnabled()))
                    .collect(Collectors.toList());
        } else {
            return users.stream()
                    .map(user -> new UserDTO(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getPhone(),
                            user.getFullName(),
                            user.isEnabled(),
                            user.getImage())
                    )
                    .collect(Collectors.toList());
        }
    }

    public User saveUser(UserCreateTO userTO) {

        var encodedPassword = Base64.getEncoder().encodeToString(userTO.getPassword().getBytes());
        var user = User.builder()
                .username(userTO.getUsername())
                .password(encodedPassword)
                .phone(userTO.getPhone())
                .email(userTO.getEmail())
                .enabled(true)
                .fullName(userTO.getFullName())
                .build();
        return userRepository.save(user);
    }

    public User updateUser(UserTO userTO, User user) {
        user.setFullName(userTO.getFullName());
        user.setEmail(userTO.getEmail());
        user.setPhone(userTO.getPhone());
        user.setEnabled(userTO.isEnabled());

        return userRepository.save(user);
    }

    public void deleteUser(User userToDelete) {
        userRepository.delete(userToDelete);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
