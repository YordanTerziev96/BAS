package com.brokerage_agency_system.service;

import com.brokerage_agency_system.DTO.EstateFilterDTO;
import com.brokerage_agency_system.DTO.EstateTO;
import com.brokerage_agency_system.DTO.OwnerCreateTO;
import com.brokerage_agency_system.exception.InvalidFileTypeException;
import com.brokerage_agency_system.model.Estate;
import com.brokerage_agency_system.DTO.EstateCreateTO;
import com.brokerage_agency_system.model.Image;
import com.brokerage_agency_system.model.Location;
import com.brokerage_agency_system.model.Owner;
import com.brokerage_agency_system.repository.EstateRepository;
import com.brokerage_agency_system.repository.ImageRepository;
import com.brokerage_agency_system.repository.LocationRepository;
import com.brokerage_agency_system.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class EstateService {

    private final EstateRepository estateRepository;
    private final OwnerRepository ownerRepository;
    private final ImageRepository imageRepository;
    private final LocationRepository locationRepository;

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
        estate.setEstateType(createTO.getEstateType());
        estate.setDescription(createTO.getDescription());
        estate.setCoordinates(createTO.getCoordinates());
        estate.setComments(createTO.getComments());
        estate.setPrice(createTO.getPrice());
        Optional.ofNullable(createTO.getImages()).ifPresent(estate::setImages);

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
        Optional.ofNullable(estateTO.getStatus()).ifPresent(estate::setStatus);
        Optional.ofNullable(estateTO.getEstateType()).ifPresent(estate::setEstateType);
        Optional.ofNullable(estateTO.getDescription()).ifPresent(estate::setDescription);
        Optional.ofNullable(estateTO.getCoordinates()).ifPresent(estate::setCoordinates);
        Optional.ofNullable(estateTO.getComments()).ifPresent(estate::setComments);
        Optional.ofNullable(estateTO.getPrice()).ifPresent(estate::setPrice);

        return estateRepository.save(estate);
    }

    public Optional<Estate> getEstateById(String estateId) {
        return estateRepository.findById(estateId);
    }

    public Estate saveImages(List<MultipartFile> images, Estate estate) throws IOException, InvalidFileTypeException {
        for (MultipartFile file : images) {
            String mimeType = file.getContentType();
            if (mimeType == null || !mimeType.startsWith("image/")) {
                throw new InvalidFileTypeException("Only image files are allowed.");
            }

            var image = Image.builder()
                    .data(file.getBytes())
                    .mimeType(file.getContentType())
                    .build();

            estate.getImages().add(image);
        }

        return estateRepository.save(estate);
    }

    public List<Image> getImages(Estate estate) {
        return estate.getImages();
    }

    public List<Estate> filterEstates(EstateFilterDTO filterDTO) {
        List<Estate> estates = estateRepository.findAll();
        return estates.stream()
                .filter(estate -> filterDTO.getStatus() == null || estate.getStatus().equals(filterDTO.getStatus()))
                .filter(estate -> filterDTO.getEstateType() == null || estate.getEstateType().equals(filterDTO.getEstateType()))
                .filter(estate -> filterDTO.getOwnerId() == null || estate.getOwner().getId().equals(filterDTO.getOwnerId()))
                .filter(estate -> filterDTO.getMinPrice() == null || estate.getPrice() >= filterDTO.getMinPrice())
                .filter(estate -> filterDTO.getMaxPrice() == null || estate.getPrice() <= filterDTO.getMaxPrice())
                .filter(estate -> filterDTO.getNeighbourhood() == null || estate.getLocation().getNeighbourhood().equals(filterDTO.getNeighbourhood()))
                .collect(Collectors.toList());
    }

    public void deleteOwner(Owner ownerToDelete) {
        ownerRepository.delete(ownerToDelete);
    }

    public void deleteFile(Image imageToDelete) {
        imageRepository.delete(imageToDelete);
    }

    public void deleteEstate(Estate estateToDelete) {
        imageRepository.deleteAll(estateToDelete.getImages());
        estateRepository.delete(estateToDelete);
    }

    public List<String> getAllNeighbourhoods() {
        var allLocations = locationRepository.findAll();
        return allLocations.stream()
                .map(Location::getNeighbourhood)
                .collect(Collectors.toList());
    }
}
