package com.lms.dev.controller;

import com.lms.dev.dto.ApiResponse;
import com.lms.dev.entity.Certificate;
import com.lms.dev.security.UserPrincipal;
import com.lms.dev.service.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
@Slf4j
public class CertificateController {

    private final CertificateService certificateService;

    /**
     * Generate certificate for a user upon course completion
     * Accessible by ADMIN, INSTRUCTOR, and USER (for their own certificates)
     */
    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'USER')")
    public ResponseEntity<ApiResponse<Certificate>> generateCertificate(
            @RequestParam UUID userId,
            @RequestParam UUID courseId,
            Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            UUID currentUserId = userPrincipal.getId();
            
            // Users can only generate certificates for themselves
            // Admins and Instructors can generate for anyone
            if (!userPrincipal.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN") &&
                !userPrincipal.getAuthorities().iterator().next().getAuthority().equals("ROLE_INSTRUCTOR")) {
                if (!currentUserId.equals(userId)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(new ApiResponse<>("You can only generate certificates for yourself", null));
                }
            }
            
            Certificate certificate = certificateService.generateCertificate(userId, courseId);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("Certificate generated successfully", certificate));
        } catch (IOException e) {
            log.error("Error generating certificate", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Failed to generate certificate: " + e.getMessage(), null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(e.getMessage(), null));
        }
    }

    /**
     * Get certificate for a user and course
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'USER')")
    public ResponseEntity<ApiResponse<Certificate>> getCertificate(
            @RequestParam UUID userId,
            @RequestParam UUID courseId,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UUID currentUserId = userPrincipal.getId();
        
        // Users can only view their own certificates
        // Admins and Instructors can view any
        if (!userPrincipal.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN") &&
            !userPrincipal.getAuthorities().iterator().next().getAuthority().equals("ROLE_INSTRUCTOR")) {
            if (!currentUserId.equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>("You can only view your own certificates", null));
            }
        }
        
        return certificateService.getCertificate(userId, courseId)
                .map(cert -> ResponseEntity.ok(new ApiResponse<>("Certificate found", cert)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Certificate not found", null)));
    }

    /**
     * Get all certificates for a user
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR', 'USER')")
    public ResponseEntity<ApiResponse<List<Certificate>>> getUserCertificates(
            @PathVariable UUID userId,
            Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UUID currentUserId = userPrincipal.getId();
        
        // Users can only view their own certificates
        // Admins and Instructors can view any
        if (!userPrincipal.getAuthorities().iterator().next().getAuthority().equals("ROLE_ADMIN") &&
            !userPrincipal.getAuthorities().iterator().next().getAuthority().equals("ROLE_INSTRUCTOR")) {
            if (!currentUserId.equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ApiResponse<>("You can only view your own certificates", null));
            }
        }
        
        List<Certificate> certificates = certificateService.getUserCertificates(userId);
        return ResponseEntity.ok(new ApiResponse<>("Certificates retrieved successfully", certificates));
    }
}

