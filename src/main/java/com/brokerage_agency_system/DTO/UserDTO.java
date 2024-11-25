package com.brokerage_agency_system.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String fullName;
    private boolean enabled;
    private byte[] image;

    public UserDTO(final String fullName, final byte[] image, final boolean enabled) {
        this.fullName = fullName;
        this.image = image;
        this.enabled = enabled;
    }
}
