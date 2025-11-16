package com.lms.dev.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "firebase")
@Data
public class FirebaseProperties {
    private Storage storage = new Storage();
    private Credentials credentials = new Credentials();

    @Data
    public static class Storage {
        private String bucketName;
        private String baseUrl;
    }

    @Data
    public static class Credentials {
        private String path;
    }
}

