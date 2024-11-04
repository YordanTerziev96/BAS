package com.brokerage_agency_system.service;

import com.brokerage_agency_system.DTO.ApiResponseDTO;
import com.brokerage_agency_system.DTO.SignInRequestDTO;
import com.brokerage_agency_system.DTO.SignInResponseDTO;
import com.brokerage_agency_system.DTO.UserCreateTO;
import com.brokerage_agency_system.exception.PasswordMismatchException;
import com.brokerage_agency_system.exception.RoleNotFoundException;
import com.brokerage_agency_system.exception.UserAlreadyExistsException;
import com.brokerage_agency_system.model.User;
import com.brokerage_agency_system.repository.RoleRepository;
import com.brokerage_agency_system.security.JwtUtils;
import com.brokerage_agency_system.security.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<ApiResponseDTO<?>> signUpUser(UserCreateTO userCreateTO)
            throws UserAlreadyExistsException, RoleNotFoundException, PasswordMismatchException {
        if (userService.existsByEmail(userCreateTO.getEmail())) {
            throw new UserAlreadyExistsException("Registration Failed: Provided email already exists. Try sign in or provide another email.");
        }
        if (userService.existsByUsername(userCreateTO.getUsername())) {
            throw new UserAlreadyExistsException("Registration Failed: Provided username already exists. Try sign in or provide another username.");
        }
        if (!userCreateTO.getPassword().equals(userCreateTO.getRepeatedPassword())) {
            throw new PasswordMismatchException("Registration Failed: Passwords don't match.");
        }

        User user = createUser(userCreateTO);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDTO.builder()
                        .isSuccess(true)
                        .message("User account has been successfully created!")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> signInUser(SignInRequestDTO signInRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequestDTO.getUsername(), signInRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        var signInResponseDTO = SignInResponseDTO.builder()
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .id(userDetails.getId())
                .token(jwt)
                .type("Bearer")
                .roles(roles)
                .build();

        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .isSuccess(true)
                        .message("Sign in successfull!")
                        .response(signInResponseDTO)
                        .build()
        );
    }

    private User createUser(UserCreateTO userCreateTO) throws RoleNotFoundException {
        return User.builder()
                .email(userCreateTO.getEmail())
                .username(userCreateTO.getUsername())
                .password(passwordEncoder.encode(userCreateTO.getPassword()))
                .phone(userCreateTO.getPhone())
                .description(userCreateTO.getDescription())
                .enabled(true)
                .roles(Set.of(roleRepository.findByName(RoleEnum.ROLE_USER)))
                .build();
    }
}
