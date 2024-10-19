package com.brokerage_agency_system.DTO;

import lombok.Data;

@Data
public class EstateFilterDTO {
    private String status;
    private Long ownerId;
    private Long minPrice;
    private Long maxPrice;
    private String neighbourhood;
}
