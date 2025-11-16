package com.lms.dev.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws")
@Data
public class AwsProperties {
    private S3 s3 = new S3();

    @Data
    public static class S3 {
        private String bucketName;
        private String region;
        private String accessKey;
        private String secretKey;
        private String baseUrl;
    }
}

