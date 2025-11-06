package com.lms.dev.service.storage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@ConditionalOnProperty(name = "storage.provider", havingValue = "firebase", matchIfMissing = false)
public class FirebaseStorageService implements StorageService {

    private final Storage storage;
    
    @Value("${firebase.storage.bucket-name}")
    private String bucketName;
    
    @Value("${firebase.storage.base-url:}")
    private String baseUrl;

    public FirebaseStorageService(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        try {
            String fileName = generateFileName(file.getOriginalFilename());
            String blobPath = folder != null && !folder.isEmpty() ? folder + "/" + fileName : fileName;
            
            BlobId blobId = BlobId.of(bucketName, blobPath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();
            
            Blob blob = storage.create(blobInfo, file.getBytes());
            
            String fileUrl = baseUrl != null && !baseUrl.isEmpty()
                    ? baseUrl + "/" + blobPath
                    : blob.getMediaLink();
            
            log.info("File uploaded to Firebase Storage: {}", fileUrl);
            return fileUrl;
        } catch (Exception e) {
            log.error("Error uploading file to Firebase Storage", e);
            throw new IOException("Failed to upload file to Firebase Storage: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            String blobPath = extractBlobPathFromUrl(fileUrl);
            BlobId blobId = BlobId.of(bucketName, blobPath);
            boolean deleted = storage.delete(blobId);
            log.info("File deleted from Firebase Storage: {}", blobPath);
            return deleted;
        } catch (Exception e) {
            log.error("Error deleting file from Firebase Storage", e);
            return false;
        }
    }

    @Override
    public boolean fileExists(String fileUrl) {
        try {
            String blobPath = extractBlobPathFromUrl(fileUrl);
            BlobId blobId = BlobId.of(bucketName, blobPath);
            Blob blob = storage.get(blobId);
            return blob != null && blob.exists();
        } catch (Exception e) {
            log.error("Error checking file existence in Firebase Storage", e);
            return false;
        }
    }

    @Override
    public String getProviderType() {
        return "FIREBASE";
    }

    private String generateFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }

    private String extractBlobPathFromUrl(String fileUrl) {
        // Extract blob path from Firebase Storage URL
        // Format: https://firebasestorage.googleapis.com/v0/b/bucket/o/path%2Ffile?alt=media
        if (fileUrl.contains("firebasestorage.googleapis.com")) {
            int start = fileUrl.indexOf("/o/") + 3;
            int end = fileUrl.indexOf("?");
            if (end == -1) end = fileUrl.length();
            return java.net.URLDecoder.decode(fileUrl.substring(start, end), java.nio.charset.StandardCharsets.UTF_8);
        } else if (baseUrl != null && fileUrl.startsWith(baseUrl)) {
            return fileUrl.substring(baseUrl.length() + 1);
        }
        return fileUrl;
    }
}

