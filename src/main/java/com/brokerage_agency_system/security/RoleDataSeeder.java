package com.brokerage_agency_system.security;

import com.brokerage_agency_system.model.Role;
import com.brokerage_agency_system.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleDataSeeder {
    @Autowired
    private RoleRepository roleRepository;

    @EventListener
    @Transactional
    public void LoadRoles(ContextRefreshedEvent event) {

        List<RoleEnum> roles = Arrays.stream(RoleEnum.values()).toList();

        for (RoleEnum roleEnum : roles) {
            if (roleRepository.findByName(roleEnum) == null) {
                roleRepository.save(new Role(roleEnum));
            }
        }
    }
}
