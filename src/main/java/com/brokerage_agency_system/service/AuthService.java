package com.brokerage_agency_system.service;

import com.brokerage_agency_system.DTO.ApiResponseDTO;
import com.brokerage_agency_system.DTO.SignInRequestDTO;
import com.brokerage_agency_system.DTO.SignUpRequestDTO;
import com.brokerage_agency_system.exception.RoleNotFoundException;
import com.brokerage_agency_system.exception.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDTO<?>> signUpUser(SignUpRequestDTO signUpRequestDto) throws UserAlreadyExistsException, RoleNotFoundException;
    ResponseEntity<ApiResponseDTO<?>> signInUser(SignInRequestDTO signInRequestDto);
}
