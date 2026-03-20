package com.smartlogistics.smart_logistics_backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CorsConfigTest {

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Test
    void testCorsConfigurationBeanExists() {
        assertNotNull(corsConfigurationSource);
    }

    @Test
    void testCorsConfigurationForLocalhost() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfig = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfig);
    }

    @Test
    void testCorsConfigurationAllowsRequiredMethods() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfig = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfig);
        assertTrue(corsConfig.getAllowedMethods().contains("GET"));
        assertTrue(corsConfig.getAllowedMethods().contains("POST"));
        assertTrue(corsConfig.getAllowedMethods().contains("PUT"));
        assertTrue(corsConfig.getAllowedMethods().contains("DELETE"));
        assertTrue(corsConfig.getAllowedMethods().contains("OPTIONS"));
        assertTrue(corsConfig.getAllowedMethods().contains("PATCH"));
    }

    @Test
    void testCorsConfigurationAllowsAllHeaders() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfig = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfig);
        assertTrue(corsConfig.getAllowedHeaders().contains("*"));
    }

    @Test
    void testCorsConfigurationAllowsCredentials() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfig = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfig);
        assertTrue(corsConfig.getAllowCredentials());
    }

    @Test
    void testCorsMaxAge() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        var corsConfig = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfig);
        assertEquals(3600L, corsConfig.getMaxAge());
    }

    @Test
    void testOriginConfigurationExists() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/test");
        request.addHeader("Origin", "http://localhost:3000");
        var corsConfig = corsConfigurationSource.getCorsConfiguration(request);
        assertNotNull(corsConfig);
        assertEquals(3600L, corsConfig.getMaxAge());
    }
}
