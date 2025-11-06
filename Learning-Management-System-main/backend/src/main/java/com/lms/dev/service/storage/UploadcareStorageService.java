package com.lms.dev.service.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
@ConditionalOnProperty(name = "storage.provider", havingValue = "uploadcare", matchIfMissing = false)
public class UploadcareStorageService implements StorageService {

    private final HttpClient httpClient;
    
    @Value("${uploadcare.public-key}")
    private final String publicKey;
    
    @Value("${uploadcare.secret-key}")
    private final String secretKey;
    
    private static final String UPLOADCARE_UPLOAD_URL = "https://upload.uploadcare.com/base/";
    private static final String UPLOADCARE_API_URL = "https://api.uploadcare.com/files/";

    public UploadcareStorageService(@Value("${uploadcare.public-key}") String publicKey,
                                   @Value("${uploadcare.secret-key}") String secretKey) {
        this.publicKey = publicKey;
        this.secretKey = secretKey;
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        try {
            // Uploadcare REST API: POST /base/ with multipart form data
            byte[] fileBytes = file.getBytes();
            String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();
            
            // Build multipart form data
            StringBuilder formData = new StringBuilder();
            formData.append("--").append(boundary).append("\r\n");
            formData.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getOriginalFilename()).append("\"\r\n");
            formData.append("Content-Type: ").append(file.getContentType()).append("\r\n\r\n");
            
            byte[] formDataStart = formData.toString().getBytes(StandardCharsets.UTF_8);
            String formDataEnd = "\r\n--" + boundary + "--\r\n";
            byte[] formDataEndBytes = formDataEnd.getBytes(StandardCharsets.UTF_8);
            
            // Combine all parts
            byte[] multipartData = new byte[formDataStart.length + fileBytes.length + formDataEndBytes.length];
            System.arraycopy(formDataStart, 0, multipartData, 0, formDataStart.length);
            System.arraycopy(fileBytes, 0, multipartData, formDataStart.length, fileBytes.length);
            System.arraycopy(formDataEndBytes, 0, multipartData, formDataStart.length + fileBytes.length, formDataEndBytes.length);
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(UPLOADCARE_UPLOAD_URL))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .header("Authorization", "Uploadcare.Simple " + publicKey + ":" + secretKey)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(multipartData))
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                // Parse response to get file ID
                String responseBody = response.body();
                String fileId = extractFileIdFromResponse(responseBody);
                String fileUrl = "https://ucarecdn.com/" + fileId + "/";
                log.info("File uploaded to Uploadcare: {}", fileUrl);
                return fileUrl;
            } else {
                throw new IOException("Uploadcare API returned status: " + response.statusCode());
            }
        } catch (Exception e) {
            log.error("Error uploading file to Uploadcare", e);
            throw new IOException("Failed to upload file to Uploadcare: " + e.getMessage(), e);
        }
    }
    
    private String extractFileIdFromResponse(String responseBody) {
        // Uploadcare response typically contains file ID in JSON format
        // Simple extraction - in production, use proper JSON parsing
        if (responseBody.contains("\"file\"")) {
            int start = responseBody.indexOf("\"file\"") + 8;
            int end = responseBody.indexOf("\"", start);
            return responseBody.substring(start, end);
        }
        // Fallback: assume response is just the file ID
        return responseBody.trim().replace("\"", "");
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            String fileId = extractFileIdFromUrl(fileUrl);
            String auth = Base64.getEncoder().encodeToString((publicKey + ":" + secretKey).getBytes(StandardCharsets.UTF_8));
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(UPLOADCARE_API_URL + fileId + "/"))
                    .header("Authorization", "Basic " + auth)
                    .DELETE()
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 204 || response.statusCode() == 200) {
                log.info("File deleted from Uploadcare: {}", fileId);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Error deleting file from Uploadcare", e);
            return false;
        }
    }

    @Override
    public boolean fileExists(String fileUrl) {
        try {
            String fileId = extractFileIdFromUrl(fileUrl);
            String auth = Base64.getEncoder().encodeToString((publicKey + ":" + secretKey).getBytes(StandardCharsets.UTF_8));
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(UPLOADCARE_API_URL + fileId + "/"))
                    .header("Authorization", "Basic " + auth)
                    .GET()
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            log.error("Error checking file existence in Uploadcare", e);
            return false;
        }
    }

    @Override
    public String getProviderType() {
        return "UPLOADCARE";
    }

    private String extractFileIdFromUrl(String fileUrl) {
        // Uploadcare URL format: https://ucarecdn.com/{fileId}/
        if (fileUrl.contains("ucarecdn.com")) {
            int start = fileUrl.indexOf("ucarecdn.com/") + 13;
            int end = fileUrl.indexOf("/", start);
            if (end == -1) end = fileUrl.length();
            return fileUrl.substring(start, end);
        }
        return fileUrl;
    }
}

