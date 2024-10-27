package com.brokerage_agency_system.controller;

import com.brokerage_agency_system.DTO.ApiResponseDTO;
import com.brokerage_agency_system.DTO.SignInRequestDTO;
import com.brokerage_agency_system.DTO.UserCreateTO;
import com.brokerage_agency_system.exception.PasswordMismatchException;
import com.brokerage_agency_system.exception.RoleNotFoundException;
import com.brokerage_agency_system.exception.UserAlreadyExistsException;
import com.brokerage_agency_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO<?>> registerUser(@RequestBody @Valid UserCreateTO userCreateTO)
            throws UserAlreadyExistsException, RoleNotFoundException, PasswordMismatchException {
        return authService.signUpUser(userCreateTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponseDTO<?>> signInUser(@RequestBody @Valid SignInRequestDTO signInRequestDTO) {
        return authService.signInUser(signInRequestDTO);
    }
}
