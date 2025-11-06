package com.lms.dev.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@ConditionalOnProperty(name = "storage.provider", havingValue = "firebase")
@Slf4j
public class FirebaseConfig {

    @Value("${firebase.credentials.path:}")
    private String credentialsPath;

    @Bean
    public Storage firebaseStorage() throws IOException {
        if (credentialsPath == null || credentialsPath.isEmpty()) {
            log.warn("Firebase credentials path not set. Using default credentials.");
            return StorageOptions.getDefaultInstance().getService();
        }
        
        try (FileInputStream serviceAccount = new FileInputStream(credentialsPath)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            return StorageOptions.newBuilder()
                    .setCredentials(credentials)
                    .build()
                    .getService();
        }
    }
}

