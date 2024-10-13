package com.brokerage_agency_system.repository;

import com.brokerage_agency_system.model.Estate;
import com.brokerage_agency_system.model.Owner;
import com.brokerage_agency_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, String> {
    Optional<Owner> findByEmail(String email);
}
