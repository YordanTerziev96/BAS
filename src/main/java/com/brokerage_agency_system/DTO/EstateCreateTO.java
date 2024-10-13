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
public class EstateCreateTO {

    @NotBlank(message = "Status cannot be none")
    private String status;

    @NotBlank(message = "User cannot be none")
    private User user;

    private List<Image> images;

    private String description;

    @NotBlank(message = "Owner cannot be none")
    private Owner owner;

    @NotBlank(message = "Coordinates cannot be none")
    private String coordinates;

    private List<String> comments;

    @NotBlank(message = "Price cannot be none")
    private Long price;
}
