package com.lms.dev.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Interface for file storage services
 * Supports multiple storage providers: AWS S3, Uploadcare, Firebase Storage
 */
public interface StorageService {
    
    /**
     * Upload a file and return the public URL
     * @param file The file to upload
     * @param folder The folder/path where the file should be stored
     * @return The public URL of the uploaded file
     * @throws IOException if upload fails
     */
    String uploadFile(MultipartFile file, String folder) throws IOException;
    
    /**
     * Delete a file by its URL
     * @param fileUrl The URL of the file to delete
     * @return true if deletion was successful
     */
    boolean deleteFile(String fileUrl);
    
    /**
     * Check if a file exists at the given URL
     * @param fileUrl The URL to check
     * @return true if file exists
     */
    boolean fileExists(String fileUrl);
    
    /**
     * Get the storage provider type
     * @return The provider name (e.g., "S3", "UPLOADCARE", "FIREBASE")
     */
    String getProviderType();
}

