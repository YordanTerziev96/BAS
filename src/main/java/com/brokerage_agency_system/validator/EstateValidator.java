package com.brokerage_agency_system.validator;

import com.brokerage_agency_system.DTO.EstateCreateTO;
import com.brokerage_agency_system.DTO.EstateTO;
import com.brokerage_agency_system.DTO.OwnerCreateTO;
import com.brokerage_agency_system.model.Estate;
import com.brokerage_agency_system.model.Owner;
import com.brokerage_agency_system.repository.EstateRepository;
import com.brokerage_agency_system.repository.OwnerRepository;
import com.brokerage_agency_system.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Component
public class EstateValidator {

    UserRepository userRepository;
    OwnerRepository ownerRepository;
    EstateRepository estateRepository;

    public Estate validateForCreate(EstateCreateTO estateCreateTO) {
        if (estateCreateTO == null) {
            throw new NullPointerException("Empty Estate object");
        }
        if (estateCreateTO.getUsername().isEmpty()) {
            throw new NullPointerException("Empty username.");
        }
        var existingUser = userRepository.findByUsername(estateCreateTO.getUsername());
        if (existingUser.isEmpty()) {
            throw new NoSuchElementException("There is no such user with username: " + estateCreateTO.getUsername());
        }
        if (estateCreateTO.getOwnerId() == null) {
            throw new NullPointerException("Empty owner id");
        }
        var existingOwner = ownerRepository.findById(String.valueOf(estateCreateTO.getOwnerId()));
        if (existingOwner.isEmpty()) {
            throw new NoSuchElementException("There is no such owner with id: " + estateCreateTO.getOwnerId());
        }

        return Estate.builder().user(existingUser.get()).owner(existingOwner.get()).build();
    }

    public void validateForCreateOwner(OwnerCreateTO createTO) {
        if (createTO == null) {
            throw new NullPointerException("Empty object");
        }
        var existingOwner = ownerRepository.findByEmail(createTO.getEmail());
        if (existingOwner.isPresent()) {
            throw new IllegalArgumentException("Owner already exists!");
        }
    }

    public Owner validateForUpdateOwner(String ownerId, OwnerCreateTO createTO) {
        if (ownerId.isBlank() || createTO == null) {
            throw new NullPointerException("Empty object");
        }
        var existingOwner = ownerRepository.findById(ownerId);
        if (existingOwner.isEmpty()) {
            throw new NoSuchElementException("There is no such owner with id: " + ownerId);
        }
        return existingOwner.get();
    }

    public Estate validateForUpdate(String estateId, EstateTO estateTO) {
        if (estateId.isBlank() || estateTO == null) {
            throw new NullPointerException("Empty object");
        }
        var estate = estateRepository.findById(estateId);
        if (estate.isEmpty()) {
            throw new NoSuchElementException("There is no such estate with id: " + estateId);
        }
        if(estate.get().getOwner() == null) {
            throw new NullPointerException("Empty Owner object");
        }
        var existingOwner = ownerRepository.findById(String.valueOf(estateTO.getOwner().getId()));
        if (existingOwner.isEmpty()) {
            throw new NoSuchElementException("There is no such owner with id: " + estateTO.getOwner().getId());
        }
        return estate.get();
    }

    public Estate validateFile(List<MultipartFile> file, Long estateId) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Empty file.");
        }
        var estate = estateRepository.findById(String.valueOf(estateId));
        if (estate.isEmpty()) {
            throw new NoSuchElementException("There is no such estate with id: " + estateId);
        }
        return estate.get();
    }

    public Estate validateEstate(Long estateId) {
        if (estateId == null) {
            throw new IllegalArgumentException("Empty object.");
        }
        var estate = estateRepository.findById(String.valueOf(estateId));
        if (estate.isEmpty()) {
            throw new NoSuchElementException("There is no such estate with id: " + estateId);
        }
        return estate.get();
    }
}
