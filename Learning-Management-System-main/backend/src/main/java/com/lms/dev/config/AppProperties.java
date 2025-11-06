package com.lms.dev.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private String jwtSecret;
    private long jwtExpirationMs;
    private DefaultAdmin defaultAdmin = new DefaultAdmin();
    private DefaultInstructor defaultInstructor = new DefaultInstructor();

    // Explicit getters for Lombok compatibility
    public String getJwtSecret() {
        return jwtSecret;
    }

    public long getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public DefaultAdmin getDefaultAdmin() {
        return defaultAdmin;
    }

    public DefaultInstructor getDefaultInstructor() {
        return defaultInstructor;
    }

    @Data
    public static class DefaultAdmin {
        private String username = "admin";
        private String password = "admin123";
        private String email = "admin@gmail.com";
    }

    @Data
    public static class DefaultInstructor {
        private String username = "instructor";
        private String password = "instructor2468";
        private String email = "instructor@gmail.com";
    }
}

