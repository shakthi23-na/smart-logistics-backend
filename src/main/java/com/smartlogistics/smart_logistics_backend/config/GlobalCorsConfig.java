package com.smartlogistics.smart_logistics_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {
    // List of exactly 6 allowed origins (update as needed for your deployment)
    private static final String[] ALLOWED_ORIGINS = {
        "http://localhost:3000",
        "http://127.0.0.1:3000",
        "https://smart-logistics-frontend-mocha.vercel.app",
        "https://gray-dune-0b862e200.4.azurestaticapps.net",
        "https://your-azure-container-app-url.azurecontainerapps.io",
        "https://your-custom-domain.com"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // If wildcard is present, disable credentials
        boolean allowCredentials = true;
        for (String origin : ALLOWED_ORIGINS) {
            if ("*".equals(origin.trim())) {
                allowCredentials = false;
                break;
            }
        }
        registry.addMapping("/api/**")
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(allowCredentials);
    }
}