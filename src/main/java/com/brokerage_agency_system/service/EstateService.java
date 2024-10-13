package com.brokerage_agency_system.service;

import com.brokerage_agency_system.DTO.EstateTO;
import com.brokerage_agency_system.DTO.OwnerCreateTO;
import com.brokerage_agency_system.model.Estate;
import com.brokerage_agency_system.DTO.EstateCreateTO;
import com.brokerage_agency_system.model.Owner;
import com.brokerage_agency_system.repository.EstateRepository;
import com.brokerage_agency_system.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EstateService {

    private final EstateRepository estateRepository;
    private final OwnerRepository ownerRepository;

    public List<Estate> getAllEstates() {
        return estateRepository.findAll();
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner saveOwner(OwnerCreateTO createTO) {
        Owner owner = Owner.builder()
                .fullName(createTO.getFullName())
                .email(createTO.getEmail())
                .phone(createTO.getPhone()).build();

        return ownerRepository.save(owner);
    }

    public Estate saveEstate(EstateCreateTO estateCreateTO) {

        var images = estateCreateTO.getImages();

        var estate = Estate.builder()
                .owner(estateCreateTO.getOwner())
                .images(estateCreateTO.getImages())
                .price(estateCreateTO.getPrice())
                .comments(estateCreateTO.getComments())
                .coordinates(estateCreateTO.getCoordinates())
                .description(estateCreateTO.getDescription())
                .status(estateCreateTO.getStatus())
                .user(estateCreateTO.getUser()).build();
        return estateRepository.save(estate);
    }

    public Optional<Owner> getOwnerById(String ownerId) {
        return ownerRepository.findById(ownerId);
    }

    public Object updateOwner(Owner owner, OwnerCreateTO ownerCreateTO) {
        owner.setEmail(ownerCreateTO.getEmail());
        owner.setPhone(ownerCreateTO.getPhone());
        owner.setFullName(ownerCreateTO.getFullName());

        return ownerRepository.save(owner);
    }

    public Estate updateEstate(Estate estate, EstateTO estateTO) {
        estate.setOwner(estateTO.getOwner());
        estate.setComments(estateTO.getComments());
        estate.setImages(estateTO.getImages());
        estate.setPrice(estateTO.getPrice());
        estate.setDescription(estateTO.getDescription());
        estate.setCoordinates(estateTO.getCoordinates());
        estate.setStatus(estateTO.getStatus());

        return estateRepository.save(estate);
    }

    public Optional<Estate> getEstateById(String estateId) {
        return estateRepository.findById(estateId);
    }
}
