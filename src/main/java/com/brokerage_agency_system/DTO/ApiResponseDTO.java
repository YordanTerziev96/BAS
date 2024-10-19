package com.brokerage_agency_system.DTO;

import lombok.Builder;
import lombok.Data;

//TODO - check where it's used
@Data
@Builder
public class ApiResponseDTO<T> {
    private boolean isSuccess;
    private String message;
    private T response;
}