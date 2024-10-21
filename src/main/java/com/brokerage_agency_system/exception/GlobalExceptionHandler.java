package com.brokerage_agency_system.exception;

import com.brokerage_agency_system.DTO.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        // List to store error messages
        List<String> errorMessages = new ArrayList<>();

        // Extracting field validation error messages
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errorMessages.add(error.getDefaultMessage());
        }

        // Return the list of error messages with a 400 BAD REQUEST status
        return ResponseEntity
                .badRequest()
                .body(errorMessages);
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
