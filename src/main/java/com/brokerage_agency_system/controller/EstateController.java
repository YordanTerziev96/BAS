package com.brokerage_agency_system.controller;

import com.brokerage_agency_system.DTO.EstateCreateTO;
import com.brokerage_agency_system.DTO.EstateFilterDTO;
import com.brokerage_agency_system.DTO.EstateTO;
import com.brokerage_agency_system.DTO.OwnerCreateTO;
import com.brokerage_agency_system.exception.InvalidFileTypeException;
import com.brokerage_agency_system.exception.UserNotFoundException;
import com.brokerage_agency_system.model.Estate;
import com.brokerage_agency_system.model.Owner;
import com.brokerage_agency_system.service.EstateService;
import com.brokerage_agency_system.validator.EstateValidator;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/estates")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "http://localhost:4200")
public class EstateController {

    private final EstateService estateService;
    private final EstateValidator validator;

    /**
     * API endpoint to get all estates.
     *
     * @return a list of all estates.
     */
    @GetMapping
    public List<Estate> getAllEstates() {
        return estateService.getAllEstates();
    }

    @GetMapping("/{estateId}")
    public ResponseEntity<?> getEstate(@PathVariable String estateId) {
        var estate = estateService.getEstateById(estateId);

        return estate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<?> createEstate(@Valid @RequestBody EstateCreateTO createTO) throws UserNotFoundException {

        var validatedEstate = validator.validateForCreate(createTO);

        Estate savedEstate = estateService.saveEstate(validatedEstate, createTO);

        return ResponseEntity.status(201).body(savedEstate);

    }

    @PutMapping("/{estateId}")
    public ResponseEntity<?> updateEstate(@PathVariable String estateId, @Valid @RequestBody EstateTO estateTO) {
        var validatedEstate = validator.validateForUpdate(estateId, estateTO);
        var updatedEstate = estateService.updateEstate(validatedEstate, estateTO);
        return ResponseEntity.ok(updatedEstate);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filterEstates(@Valid @RequestBody EstateFilterDTO filter) {
        return ResponseEntity.ok().body(estateService.filterEstates(filter));
    }

    @DeleteMapping("/{estateId}")
    public ResponseEntity<?> deleteEstate(@PathVariable Long estateId) {
        var existingEstate = validator.validateEstate(estateId);
        estateService.deleteEstate(existingEstate);
        return ResponseEntity.accepted().body("Deleted estate with id: " + estateId);
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
    public ResponseEntity<?> createOwner(@Valid @RequestBody OwnerCreateTO ownerCreateTO) {
        validator.validateForCreateOwner(ownerCreateTO);
        var createdOwner = estateService.saveOwner(ownerCreateTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOwner);
    }

    @PutMapping("/owner/{ownerId}")
    public ResponseEntity<?> updateOwner(@PathVariable String ownerId, @Valid @RequestBody OwnerCreateTO ownerCreateTO) {
        var validateOwner = validator.validateForUpdateOwner(ownerId, ownerCreateTO);
        var updateOwner = estateService.updateOwner(validateOwner, ownerCreateTO);
        return ResponseEntity.ok(updateOwner);
    }

    @DeleteMapping("/owner/{ownerId}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long ownerId) {
        var existingOwner = validator.validateForDeleteOwner(ownerId);
        estateService.deleteOwner(existingOwner);
        return ResponseEntity.accepted().body("Deleted owner with id: " + ownerId);
    }

    @PostMapping(value = "/{estateId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable Long estateId,
                                          @RequestParam("file") List<MultipartFile> images) throws InvalidFileTypeException, IOException {
        var estate = validator.validateFile(images, estateId);
        var updatedEstate = estateService.saveImages(images, estate);
        return ResponseEntity.ok(updatedEstate);
    }


    @GetMapping("/{estateId}/images")
    public ResponseEntity<?> getImages(@PathVariable Long estateId) {
        var validatedEstate = validator.validateEstate(estateId);
        var images = estateService.getImages(validatedEstate);
        return ResponseEntity.ok(images);
    }


    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        var existingImage = validator.validateForDeleteFile(imageId);
        estateService.deleteFile(existingImage);
        return ResponseEntity.accepted().body("Deleted image with id: " + imageId);
    }
}
