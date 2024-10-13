package com.brokerage_agency_system.controller;

import com.brokerage_agency_system.DTO.EstateCreateTO;
import com.brokerage_agency_system.DTO.EstateTO;
import com.brokerage_agency_system.DTO.OwnerCreateTO;
import com.brokerage_agency_system.DTO.UserTO;
import com.brokerage_agency_system.model.Estate;
import com.brokerage_agency_system.model.Owner;
import com.brokerage_agency_system.model.User;
import com.brokerage_agency_system.service.EstateService;
import com.brokerage_agency_system.validator.EstateValidator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/estates")
public class EstateController {

    private final EstateService estateService;
    private final EstateValidator validator;

    /**
     * API endpoint to get all estates.
     * @return a list of all estates.
     */
    @GetMapping
    public List<Estate> getAllEstates() {
        return estateService.getAllEstates();
    }

    @GetMapping("/owners")
    public List<Owner> getAllOwners() {
        return estateService.getAllOwners();
    }

    @GetMapping("/owner")
    public ResponseEntity<?> getOwner(@RequestParam String ownerId) {
        var owner = estateService.getOwnerById(ownerId);

        return owner.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/owners")
    public ResponseEntity<?> createOwner(OwnerCreateTO ownerCreateTO) {
        try {
            validator.validateForCreateOwner(ownerCreateTO);
            var createdOwner = estateService.saveOwner(ownerCreateTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOwner);
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{estateId}")
    public ResponseEntity<?> getEstate(@RequestParam String estateId) {
        var estate = estateService.getEstateById(estateId);

        return estate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{estateId}")
    public ResponseEntity<?> updateEstate(@PathVariable String estateId, @RequestBody EstateTO estateTO) {
        try {
            var validatedEstate = validator.validateForUpdate(estateId, estateTO);
            var updatedEstate = estateService.updateEstate(validatedEstate, estateTO);
            return ResponseEntity.ok(updatedEstate);
        } catch (NullPointerException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage() + "lalalalla");
        }
    }

    @PutMapping("/owner/{ownerId}")
    public ResponseEntity<?> updateOwner(@PathVariable String ownerId, @RequestBody OwnerCreateTO ownerCreateTO) {
        try {
            var validateOwner = validator.validateForUpdateOwner(ownerId, ownerCreateTO);
            var updateOwner = estateService.updateOwner(validateOwner, ownerCreateTO);
            return ResponseEntity.ok(updateOwner);
        } catch (NullPointerException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping // POST endpoint to create a new estate
    public ResponseEntity<Estate> createEstate(@RequestBody EstateCreateTO createTO) {
        validator.validateForCreate(createTO);
        Estate savedEstate = estateService.saveEstate(createTO);

        return ResponseEntity.status(201).body(savedEstate);
    }

    //TODO Filter estates
    //TODO Add api for uploading images
}
