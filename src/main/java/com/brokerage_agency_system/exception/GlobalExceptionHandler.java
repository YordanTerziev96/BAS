package com.brokerage_agency_system.exception;

import com.brokerage_agency_system.DTO.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<?>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        List<String> errorMessage = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.add(error.getDefaultMessage());
        });
        return ResponseEntity
                .badRequest()
                .body(
                        ApiResponseDTO.builder()
                                .isSuccess(false)
                                .message("Registration Failed: Please provide valid data.")
                                .response(errorMessage)
                                .build()
                );
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDTO<?>> UserAlreadyExistsExceptionHandler(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ApiResponseDTO.builder()
                                .isSuccess(false)
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = RoleNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<?>> RoleNotFoundExceptionHandler(RoleNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponseDTO.builder()
                                .isSuccess(false)
                                .message(exception.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = PasswordMismatchException.class)
    public ResponseEntity<ApiResponseDTO<?>> PasswordMismatchExceptionHandler(PasswordMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponseDTO.builder()
                                .isSuccess(false)
                                .message(exception.getMessage())
                                .build()
                );
    }
}
