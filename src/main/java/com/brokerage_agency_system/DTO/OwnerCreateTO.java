package com.brokerage_agency_system.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class OwnerCreateTO {

    @NotBlank(message = "Full name cannot be null")
    private String fullName;

    @NotBlank(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "Phone cannot be null")
    private String phone;
}
