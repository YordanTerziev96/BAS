package com.brokerage_agency_system.DTO;


import com.brokerage_agency_system.model.Image;
import com.brokerage_agency_system.model.Owner;
import com.brokerage_agency_system.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class EstateTO {

    private String status;

    private List<Image> images;

    private String description;

    private Owner owner;

    private String coordinates;

    private List<String> comments;

    private Long price;
}
