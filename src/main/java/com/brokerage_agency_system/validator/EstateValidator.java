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

import java.util.NoSuchElementException;

@AllArgsConstructor
@Component
public class EstateValidator {

    UserRepository userRepository;
    OwnerRepository ownerRepository;
    EstateRepository estateRepository;

    public void validateForCreate(EstateCreateTO estateCreateTO) {
        if (estateCreateTO == null) {
            throw new NullPointerException("Empty Estate object");
        }
        if (estateCreateTO.getUser() == null) {
            throw new NullPointerException("Empty User object");
        }
        if (estateCreateTO.getOwner() == null) {
            throw new NullPointerException("Empty Owner object");
        }
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
}
