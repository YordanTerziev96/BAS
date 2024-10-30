package com.brokerage_agency_system.DTO;

import com.brokerage_agency_system.model.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
public class EstateCreateTO {

    @NotNull(message = "Status cannot be null")
    private EstateStatus status;

    @NotNull(message = "Estate type cannot be null")
    private EstateType estateType;

    @NotBlank(message = "Username cannot be none")
    private String username;

    private String description;

    @NotNull(message = "Owner id cannot be null")
    private Long ownerId;

    @NotBlank(message = "Coordinates cannot be none")
    private String coordinates;

    private List<String> comments;

    @NotNull(message = "Price cannot be null")
    private Long price;

    @NotBlank(message = "Neighbourhood cannot be none")
    private String neighbourhood;
}
