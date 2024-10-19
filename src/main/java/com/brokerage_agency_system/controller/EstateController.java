package com.brokerage_agency_system.controller;

import com.brokerage_agency_system.DTO.EstateCreateTO;
import com.brokerage_agency_system.DTO.EstateFilterDTO;
import com.brokerage_agency_system.DTO.EstateTO;
import com.brokerage_agency_system.DTO.OwnerCreateTO;
import com.brokerage_agency_system.model.Estate;
import com.brokerage_agency_system.model.Owner;
import com.brokerage_agency_system.service.EstateService;
import com.brokerage_agency_system.validator.EstateValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

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
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filterEstates(@RequestBody EstateFilterDTO filter) {
        return ResponseEntity.ok().body(estateService.filterEstates(filter));
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

    @PostMapping
    public ResponseEntity<?> createEstate(@RequestBody EstateCreateTO createTO) {
        try {
            var validatedEstate = validator.validateForCreate(createTO);

            Estate savedEstate = estateService.saveEstate(validatedEstate, createTO);

            return ResponseEntity.status(201).body(savedEstate);
        } catch (NoSuchElementException | NullPointerException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@RequestParam("file") List<MultipartFile> images,
                                        @RequestParam("estateId") Long estateId) {
        try {
            var estate = validator.validateFile(images, estateId);
            var updatedEstate = estateService.saveImages(images, estate);
            return ResponseEntity.ok(updatedEstate);
        } catch (IllegalArgumentException | NoSuchElementException | IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getImages")
    public ResponseEntity<?> getImages(@RequestParam("estateId") Long estateId) {

        try {
            var validatedEstate = validator.validateEstate(estateId);
            var images = estateService.getImages(validatedEstate);
            return ResponseEntity.ok(images);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //TODO Filter estates
    //TODO Delete endpoints
}
