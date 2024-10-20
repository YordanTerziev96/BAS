package com.brokerage_agency_system.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInRequestDTO {
    @NotBlank(message = "Username is required!")
    private String username;

    @NotBlank(message = "Password is required!")
    private String password;
}
