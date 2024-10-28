package com.brokerage_agency_system.DTO;

import com.brokerage_agency_system.model.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class EstateCreateTO {

    @NotBlank(message = "Status cannot be none")
    private EstateStatus status;

    @NotBlank(message = "Estate type cannot be none")
    private EstateType estateType;

    @NotBlank(message = "Username cannot be none")
    private String username;

    private String description;

    @NotBlank(message = "Owner id cannot be none")
    private Long ownerId;

    @NotBlank(message = "Coordinates cannot be none")
    private String coordinates;

    private List<String> comments;

    @NotBlank(message = "Price cannot be none")
    private Long price;

    @NotBlank(message = "Neighbourhood cannot be none")
    private String neighbourhood;
}
