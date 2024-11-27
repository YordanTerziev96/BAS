package com.brokerage_agency_system.repository;

import com.brokerage_agency_system.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, String> {

    Optional<Location> findByPostalCode(String postalCode);

    List<Location> findByPostalCodeAndNeighbourhoodLatinContaining(String postalCode, String neighbourhoodLatin);

    @Query("SELECT loc FROM Location loc WHERE loc.postalCode = :postalCode AND (loc.neighbourhood = :neighbourhood OR loc.neighbourhoodLatin = :neighbourhood)")
    List<Location> findByPostalCodeAndNeighbourhood(@Param("postalCode") String postalCode, @Param("neighbourhood") String neighbourhood);
}
