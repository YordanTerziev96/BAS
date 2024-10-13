package com.brokerage_agency_system.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class UserCreateTO {

    @NotBlank(message = "Username cannot be null")
    private String username;

    @NotBlank(message = "Password cannot be null")
    private String password;

    @NotBlank(message = "Password cannot be null")
    private String repeatedPassword;

    @NotBlank(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "Phone cannot be null")
    private String phone;

    private String description;

}