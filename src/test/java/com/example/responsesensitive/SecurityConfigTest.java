package com.example.responsesensitive;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sensitiveEndpointRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/sensitive-infos/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void sensitiveEndpointAllowsAuthenticatedAccess() throws Exception {
        mockMvc.perform(get("/sensitive-infos/user"))
                .andExpect(status().isOk());
    }

    @Test
    void publicEndpointDoesNotRequireAuthentication() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }
}