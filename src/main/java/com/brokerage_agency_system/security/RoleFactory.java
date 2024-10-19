package com.brokerage_agency_system.security;

import com.brokerage_agency_system.exception.RoleNotFoundException;
import com.brokerage_agency_system.model.Role;
import com.brokerage_agency_system.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory {
    @Autowired
    RoleRepository roleRepository;

    public Role getInstance(String role) throws RoleNotFoundException {
        switch (role) {
            case "admin" -> {
                return roleRepository.findByName(RoleEnum.ROLE_ADMIN);
            }
            case "user" -> {
                return roleRepository.findByName(RoleEnum.ROLE_USER);
            }
            case "super_admin" -> {
                return roleRepository.findByName(RoleEnum.ROLE_SUPER_ADMIN);
            }
            default -> throw  new RoleNotFoundException("No role found for " +  role);
        }
    }
}
