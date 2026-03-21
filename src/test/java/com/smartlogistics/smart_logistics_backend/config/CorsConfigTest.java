package com.smartlogistics.smart_logistics_backend.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    private CorsConfig corsConfig;

    @BeforeEach
    void setUp() {
        corsConfig = new CorsConfig();
    }

    @Test
    void testCorsConfigurationBeanExists() {
        var corsConfigurationSource = corsConfig.corsConfigurationSource();
        assertNotNull(corsConfigurationSource);
    }

    @Test
    void testCorsConfigurationAllowsRequiredMethods() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfigurationSource = corsConfig.corsConfigurationSource();
        var corsConfiguration = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfiguration);
        var allowedMethods = corsConfiguration.getAllowedMethods();
        assertNotNull(allowedMethods);
        assertTrue(allowedMethods.contains("GET"));
        assertTrue(allowedMethods.contains("POST"));
        assertTrue(allowedMethods.contains("PUT"));
        assertTrue(allowedMethods.contains("DELETE"));
        assertTrue(allowedMethods.contains("OPTIONS"));
        assertTrue(allowedMethods.contains("PATCH"));
    }

    @Test
    void testCorsConfigurationAllowsAllHeaders() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfigurationSource = corsConfig.corsConfigurationSource();
        var corsConfiguration = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfiguration);
        var allowedHeaders = corsConfiguration.getAllowedHeaders();
        assertNotNull(allowedHeaders);
        assertTrue(allowedHeaders.contains("*"));
    }

    @Test
    void testCorsConfigurationAllowsCredentials() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfigurationSource = corsConfig.corsConfigurationSource();
        var corsConfiguration = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfiguration);
        Boolean allowCredentials = corsConfiguration.getAllowCredentials();
        assertNotNull(allowCredentials);
        assertTrue(allowCredentials);
    }

    @Test
    void testCorsMaxAge() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfigurationSource = corsConfig.corsConfigurationSource();
        var corsConfiguration = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfiguration);
        assertEquals(3600L, corsConfiguration.getMaxAge());
    }

    @Test
    void testCorsConfigurationIncludesRequiredOrigins() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfigurationSource = corsConfig.corsConfigurationSource();
        var corsConfiguration = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfiguration);
        var allowedOrigins = corsConfiguration.getAllowedOrigins();
        assertNotNull(allowedOrigins);
        
        assertTrue(allowedOrigins.contains("http://localhost:3000"));
        assertTrue(allowedOrigins.contains("http://localhost:5173"));
        assertTrue(allowedOrigins.contains("http://127.0.0.1:3000"));
        assertTrue(allowedOrigins.contains("http://127.0.0.1:5173"));
        assertTrue(allowedOrigins.contains("https://smart-logistics-frontend-mocha.vercel.app"));
    }

    @Test
    void testCorsConfigurationOriginCount() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfigurationSource = corsConfig.corsConfigurationSource();
        var corsConfiguration = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfiguration);
        var allowedOrigins = corsConfiguration.getAllowedOrigins();
        assertNotNull(allowedOrigins);
        assertEquals(5, allowedOrigins.size());
    }
}
