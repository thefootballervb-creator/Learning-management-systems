package com.lms.dev.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "uploadcare")
@Data
public class UploadcareProperties {
    private String publicKey;
    private String secretKey;
}

