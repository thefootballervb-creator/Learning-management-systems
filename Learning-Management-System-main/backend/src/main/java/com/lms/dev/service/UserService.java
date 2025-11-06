package com.lms.dev.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lms.dev.entity.User;
import com.lms.dev.enums.UserRole;
import com.lms.dev.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        if (id == null) {
            return null;
        }
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        // Security: Prevent ADMIN role assignment during registration
        // Only system can create ADMIN users (via initializers)
        if (user.getRole() == UserRole.ADMIN) {
            log.warn("Attempt to register as ADMIN denied. Setting role to USER for email: {}", user.getEmail());
            user.setRole(UserRole.USER);
        }
        
        // If role is null, default to USER
        if (user.getRole() == null) {
            user.setRole(UserRole.USER);
        }
        
        // Only allow USER and INSTRUCTOR roles during registration
        if (user.getRole() != UserRole.USER && user.getRole() != UserRole.INSTRUCTOR) {
            log.warn("Invalid role specified during registration. Setting role to USER for email: {}", user.getEmail());
            user.setRole(UserRole.USER);
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Creating user with role: {} for email: {}", user.getRole(), user.getEmail());
        return userRepository.save(user);
    }

    public void updateUserProfile(MultipartFile file, UUID id) throws IOException {
        if (id == null) {
            return;
        }
        User user = getUserById(id);
        if (user == null) return;
        user.setProfileImage(file.getBytes());
        userRepository.save(user);
    }

    public User updateUser(UUID id, User updatedUser) {
        if (id == null) {
            return null;
        }
        if (updatedUser == null) {
            throw new IllegalArgumentException("Updated user cannot be null");
        }
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setDob(updatedUser.getDob());
            existingUser.setMobileNumber(updatedUser.getMobileNumber());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setLocation(updatedUser.getLocation());
            existingUser.setProfession(updatedUser.getProfession());
            existingUser.setLinkedin_url(updatedUser.getLinkedin_url());
            existingUser.setGithub_url(updatedUser.getGithub_url());
            return userRepository.save(existingUser);
        }
        return null;
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User authenticateUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public void deleteUser(UUID id) {
        if (id == null) {
            return;
        }
        userRepository.deleteById(id);
    }
}
