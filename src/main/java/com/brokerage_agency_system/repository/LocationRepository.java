package com.brokerage_agency_system.repository;

import com.brokerage_agency_system.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, String> {

    Optional<Location> findByPostalCode(String postalCode);
}
