package com.lms.dev.controller;

import com.lms.dev.dto.ApiResponse;
import com.lms.dev.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

    private final StorageService storageService;

    /**
     * Upload a file (PDF, video, audio, etc.)
     * Accessible by ADMIN and INSTRUCTOR
     */
    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<String>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", defaultValue = "uploads") String folder) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>("File is empty", null));
            }
            
            String fileUrl = storageService.uploadFile(file, folder);
            log.info("File uploaded successfully: {}", fileUrl);
            return ResponseEntity.ok(new ApiResponse<>("File uploaded successfully", fileUrl));
        } catch (IOException e) {
            log.error("Error uploading file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Failed to upload file: " + e.getMessage(), null));
        }
    }

    /**
     * Delete a file by URL
     * Accessible by ADMIN and INSTRUCTOR
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    public ResponseEntity<ApiResponse<Void>> deleteFile(@RequestParam("fileUrl") String fileUrl) {
        boolean deleted = storageService.deleteFile(fileUrl);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>("File deleted successfully", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("Failed to delete file", null));
    }

    /**
     * Check if a file exists
     */
    @GetMapping("/exists")
    public ResponseEntity<ApiResponse<Boolean>> fileExists(@RequestParam("fileUrl") String fileUrl) {
        boolean exists = storageService.fileExists(fileUrl);
        return ResponseEntity.ok(new ApiResponse<>("File existence checked", exists));
    }
}

