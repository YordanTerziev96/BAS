package com.brokerage_agency_system.repository;

import com.brokerage_agency_system.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
