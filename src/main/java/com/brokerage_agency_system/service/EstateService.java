package com.brokerage_agency_system.service;

import com.brokerage_agency_system.DTO.EstateTO;
import com.brokerage_agency_system.DTO.OwnerCreateTO;
import com.brokerage_agency_system.model.Estate;
import com.brokerage_agency_system.DTO.EstateCreateTO;
import com.brokerage_agency_system.model.Image;
import com.brokerage_agency_system.model.Owner;
import com.brokerage_agency_system.repository.EstateRepository;
import com.brokerage_agency_system.repository.OwnerRepository;
import com.brokerage_agency_system.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class EstateService {

    private final EstateRepository estateRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

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

    public Estate saveEstate(Estate estate, EstateCreateTO createTO) {

        estate.setStatus(createTO.getStatus());
        estate.setDescription(createTO.getDescription());
        estate.setCoordinates(createTO.getCoordinates());
        estate.setComments(createTO.getComments());
        estate.setPrice(createTO.getPrice());
        estate.setImages(new ArrayList<>());

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
        estate.setPrice(estateTO.getPrice());
        estate.setDescription(estateTO.getDescription());
        estate.setCoordinates(estateTO.getCoordinates());
        estate.setStatus(estateTO.getStatus());

        return estateRepository.save(estate);
    }

    public Optional<Estate> getEstateById(String estateId) {
        return estateRepository.findById(estateId);
    }

    public Estate saveImages(List<MultipartFile> images, Estate estate) throws IOException {
        for (MultipartFile i : images) {
            var image = Image.builder()
                    .data(i.getBytes())
                    .build();

            estate.getImages().add(image);
        }
        return estateRepository.save(estate);
    }

    public List<Image> getImages(Estate estate) {
        return estate.getImages();
    }
}
