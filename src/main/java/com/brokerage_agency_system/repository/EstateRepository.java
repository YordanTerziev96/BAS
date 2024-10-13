package com.brokerage_agency_system.repository;


import com.brokerage_agency_system.model.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateRepository extends JpaRepository<Estate, String> {
}

