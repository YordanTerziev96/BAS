package com.brokerage_agency_system.DTO;

import com.brokerage_agency_system.model.EstateStatus;
import com.brokerage_agency_system.model.EstateType;
import lombok.Data;

@Data
public class EstateFilterDTO {
    private EstateStatus status;
    private EstateType estateType;
    private Long ownerId;
    private Long minPrice;
    private Long maxPrice;
    private String neighbourhood;
}
