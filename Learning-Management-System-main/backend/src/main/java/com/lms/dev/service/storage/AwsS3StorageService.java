package com.lms.dev.service.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@ConditionalOnProperty(name = "storage.provider", havingValue = "s3", matchIfMissing = false)
public class AwsS3StorageService implements StorageService {

    private final S3Client s3Client;
    
    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    
    @Value("${aws.s3.region:us-east-1}")
    private String region;
    
    @Value("${aws.s3.base-url:}")
    private String baseUrl; // Optional: if you have a CloudFront CDN URL

    public AwsS3StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        try {
            String fileName = generateFileName(file.getOriginalFilename());
            String key = folder != null && !folder.isEmpty() ? folder + "/" + fileName : fileName;
            
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();
            
            s3Client.putObject(putObjectRequest, 
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            
            String fileUrl = baseUrl != null && !baseUrl.isEmpty() 
                    ? baseUrl + "/" + key 
                    : String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
            
            log.info("File uploaded to S3: {}", fileUrl);
            return fileUrl;
        } catch (Exception e) {
            log.error("Error uploading file to S3", e);
            throw new IOException("Failed to upload file to S3: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            String key = extractKeyFromUrl(fileUrl);
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            
            s3Client.deleteObject(deleteRequest);
            log.info("File deleted from S3: {}", key);
            return true;
        } catch (Exception e) {
            log.error("Error deleting file from S3", e);
            return false;
        }
    }

    @Override
    public boolean fileExists(String fileUrl) {
        try {
            String key = extractKeyFromUrl(fileUrl);
            HeadObjectRequest headRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            
            s3Client.headObject(headRequest);
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (Exception e) {
            log.error("Error checking file existence in S3", e);
            return false;
        }
    }

    @Override
    public String getProviderType() {
        return "S3";
    }

    private String generateFileName(String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return UUID.randomUUID().toString() + extension;
    }

    private String extractKeyFromUrl(String fileUrl) {
        // Extract key from S3 URL
        // Format: https://bucket.s3.region.amazonaws.com/key or https://cdn.example.com/key
        if (fileUrl.contains(bucketName)) {
            int index = fileUrl.indexOf(bucketName) + bucketName.length() + 1;
            return fileUrl.substring(index);
        } else if (baseUrl != null && fileUrl.startsWith(baseUrl)) {
            return fileUrl.substring(baseUrl.length() + 1);
        }
        // Fallback: assume the URL is just the key
        return fileUrl;
    }
}

