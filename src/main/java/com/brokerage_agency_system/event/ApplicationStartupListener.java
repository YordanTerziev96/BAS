package com.brokerage_agency_system.event;

import com.brokerage_agency_system.model.User;
import com.brokerage_agency_system.repository.RoleRepository;
import com.brokerage_agency_system.security.RoleEnum;
import com.brokerage_agency_system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ApplicationStartupListener {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

    @Value("${admin.username:admin}")
    private String adminUsername;
    @Value("${admin.email:admin@admin.com}")
    private String adminEmail;
    @Value("${admin.phone:1234}")
    private String adminPhone;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    public ApplicationStartupListener(UserService userService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        final String randomPassword = new java.security.SecureRandom().ints(48, 122) // ASCII range from 48 (0) to 122 (z)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)) // filter to get digits and letters
                .limit(12) // length of password
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        User adminUser = User.builder().username(adminUsername).email(adminEmail).phone(adminPhone).password(passwordEncoder.encode(randomPassword)).roles(Set.of(roleRepository.findByName(RoleEnum.ADMIN))).enabled(true).build();
        userService.save(adminUser);
        logger.info("Admin user created");
        logger.info("Admin username: {}", adminUsername);
        logger.info("Admin email: {}", adminEmail);
        logger.info("Admin password: {}", randomPassword);
    }

}