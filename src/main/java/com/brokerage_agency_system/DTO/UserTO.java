package com.brokerage_agency_system.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class UserTO {

    private String email;

    private String phone;

    private String fullName;

    private boolean enabled;
}
