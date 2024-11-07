package com.brokerage_agency_system.DTO;


import com.brokerage_agency_system.model.EstateStatus;
import com.brokerage_agency_system.model.EstateType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class EstateTO {

    private EstateStatus status;

    private EstateType estateType;

    private String description;

    private Long ownerId;

    private String coordinates;

    private List<String> comments;

    private Long price;

    private String postalCode;
}
