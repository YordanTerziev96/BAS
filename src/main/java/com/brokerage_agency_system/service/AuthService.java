package com.brokerage_agency_system.service;

import com.brokerage_agency_system.DTO.ApiResponseDTO;
import com.brokerage_agency_system.DTO.SignInRequestDTO;
import com.brokerage_agency_system.DTO.UserCreateTO;
import com.brokerage_agency_system.exception.PasswordMismatchException;
import com.brokerage_agency_system.exception.RoleNotFoundException;
import com.brokerage_agency_system.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDTO<?>> signUpUser(UserCreateTO userCreateTO) throws UserAlreadyExistsException, RoleNotFoundException, PasswordMismatchException;

    ResponseEntity<ApiResponseDTO<?>> signInUser(SignInRequestDTO signInRequestDto);
}
