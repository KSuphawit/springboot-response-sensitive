package com.example.responsesensitive;

import com.example.responsesensitive.configs.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void testSecurityConfigurationLoads() {
        // Test that security configuration loads properly
        assertNotNull(securityConfig);
        assertNotNull(passwordEncoder);
        assertNotNull(userDetailsService);
    }

    @Test
    void testPasswordEncoderWorks() {
        // Test that password encoder is functioning
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    void testUserDetailsServiceHasAdminUser() {
        // Test that admin user exists in user details service
        var userDetails = userDetailsService.loadUserByUsername("admin");
        assertNotNull(userDetails);
        assertTrue(userDetails.getUsername().equals("admin"));
    }
}