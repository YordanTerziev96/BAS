package com.brokerage_agency_system.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

//TODO - check where it's used
@Data
@Builder
public class SignInResponseDTO<T> {
    private String username;
    private String email;
    private Long id;
    private String token;
    private String type;
    private List<String> roles;

}