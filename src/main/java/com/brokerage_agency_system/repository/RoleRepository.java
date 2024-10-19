package com.brokerage_agency_system.repository;

import com.brokerage_agency_system.model.Role;
import com.brokerage_agency_system.security.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleEnum name);
}
