package com.brokerage_agency_system.validator;

import com.brokerage_agency_system.DTO.EstateCreateTO;
import com.brokerage_agency_system.DTO.EstateTO;
import com.brokerage_agency_system.DTO.OwnerCreateTO;
import com.brokerage_agency_system.exception.ImageNotFoundException;
import com.brokerage_agency_system.exception.LocationNotFoundException;
import com.brokerage_agency_system.exception.OwnerAlreadyExistsException;
import com.brokerage_agency_system.exception.OwnerNotFoundException;
import com.brokerage_agency_system.exception.UserNotFoundException;
import com.brokerage_agency_system.model.Estate;
import com.brokerage_agency_system.model.Image;
import com.brokerage_agency_system.model.Owner;
import com.brokerage_agency_system.repository.*;
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
    ImageRepository imageRepository;
    LocationRepository locationRepository;

    public Estate validateForCreate(EstateCreateTO estateCreateTO) throws UserNotFoundException, OwnerNotFoundException, LocationNotFoundException {

        var existingUser = userRepository.findByUsername(estateCreateTO.getUsername());
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("There is no such user with username: " + estateCreateTO.getUsername());
        }

        var existingOwner = ownerRepository.findById(String.valueOf(estateCreateTO.getOwnerId()));
        if (existingOwner.isEmpty()) {
            throw new OwnerNotFoundException("There is no such owner with id: " + estateCreateTO.getOwnerId());
        }
        var location = locationRepository.findByPostalCode(estateCreateTO.getPostalCode());
        if (location.isEmpty()) {
            throw new LocationNotFoundException("There is no such town with postal code: " + estateCreateTO.getPostalCode());
        }

        return Estate.builder()
                .user(existingUser.get())
                .owner(existingOwner.get())
                .location(location.get())
                .build();
    }

    public void validateForCreateOwner(OwnerCreateTO createTO) throws OwnerAlreadyExistsException {
        var existingOwner = ownerRepository.findByEmail(createTO.getEmail());
        if (existingOwner.isPresent()) {
            throw new OwnerAlreadyExistsException("Owner already exists!");
        }
    }

    public Owner validateForUpdateOwner(String ownerId, OwnerCreateTO createTO) throws OwnerNotFoundException {

        var existingOwner = ownerRepository.findById(ownerId);
        if (existingOwner.isEmpty()) {
            throw new OwnerNotFoundException("There is no such owner with id: " + ownerId);
        }
        return existingOwner.get();
    }

    public Owner validateForDeleteOwner(Long ownerId) throws OwnerNotFoundException {

        var existingOwner = ownerRepository.findById(String.valueOf(ownerId));
        if (existingOwner.isEmpty()) {
            throw new OwnerNotFoundException("There is no such owner with id: " + ownerId);
        }
        return existingOwner.get();
    }

    public Estate validateForUpdate(String estateId, EstateTO estateTO) throws LocationNotFoundException, OwnerNotFoundException {

        var estate = estateRepository.findById(estateId);
        if (estate.isEmpty()) {
            throw new NoSuchElementException("There is no such estate with id: " + estateId);
        }
        if (estateTO.getOwnerId() != null) {
            var existingOwner = ownerRepository.findById(String.valueOf(estateTO.getOwnerId()));
            if (existingOwner.isEmpty()) {
                throw new OwnerNotFoundException("There is no such owner with id: " + estateTO.getOwnerId());
            }
            estate.get().setOwner(existingOwner.get());
        }
        var location = locationRepository.findByPostalCode(estateTO.getPostalCode());
        if (location.isEmpty()) {
            throw new LocationNotFoundException("There is no such town with postal code: " + estateTO.getPostalCode());
        }
        estate.get().setLocation(location.get());
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

    public Image validateDeleteImage(Long fileId) throws ImageNotFoundException {
        var existingImage = imageRepository.findById(String.valueOf(fileId));
        if (existingImage.isEmpty()) {
            throw new ImageNotFoundException("There is no such image with id: " + fileId);
        }
        return existingImage.get();
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
