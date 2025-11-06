package com.lms.dev.config;

import com.lms.dev.entity.User;
import com.lms.dev.enums.UserRole;
import com.lms.dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class InstructorInitializer {

    private final AppProperties appProperties;

    @Bean
    public CommandLineRunner createDefaultInstructor(UserRepository userRepository,
                                                     PasswordEncoder passwordEncoder) {
        return args -> {
            User existingInstructor = userRepository.findByEmail(appProperties.getDefaultInstructor().getEmail());
            if (existingInstructor == null) {
                User instructor = new User();
                instructor.setUsername(appProperties.getDefaultInstructor().getUsername());
                instructor.setPassword(passwordEncoder.encode(appProperties.getDefaultInstructor().getPassword()));
                instructor.setEmail(appProperties.getDefaultInstructor().getEmail());
                instructor.setRole(UserRole.INSTRUCTOR);
                instructor.setIsActive(true);
                userRepository.save(instructor);
                log.info("Default instructor user created: {}", appProperties.getDefaultInstructor().getEmail());
            } else {
                log.info("Instructor user already exists, skipping creation.");
            }
        };
    }
}

