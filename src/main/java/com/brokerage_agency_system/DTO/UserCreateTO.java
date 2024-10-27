package com.brokerage_agency_system.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class UserCreateTO {

    @NotBlank(message = "Username is required!")
    @Size(min= 3, message = "Username must have atleast 3 characters!")
    @Size(max= 20, message = "Username can have have atmost 20 characters!")
    private String username;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, message = "Password must have at least 8 characters!")
    @Size(max = 20, message = "Password can have have almost 20 characters!")
    private String password;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, message = "Password must have at least 8 characters!")
    @Size(max = 20, message = "Password can have have almost 20 characters!")
    private String repeatedPassword;

    @Email(message = "Email is not in valid format!")
    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Phone cannot be null")
    private String phone;

    private String description;

}