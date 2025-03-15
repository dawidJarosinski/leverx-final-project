package com.example.leverxfinalproject.service;


import com.example.leverxfinalproject.dto.request.LoginRequest;
import com.example.leverxfinalproject.dto.request.SellerProfileRequest;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class SellerProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("password");

    @Test
    void FindAllSortedByRatingDescending_ExecuteGet_ShouldReturnSortedListOfSellerProfileResponses() throws Exception {
        mockMvc.perform(get("/api/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    void Save_ExecutePost_ShouldCreateSellerProfile() throws Exception {
        Gson gson = new Gson();
        String email = "seller4@seller.com";
        String password = "seller";
        LoginRequest loginRequest = new LoginRequest(email, password);

        MvcResult mvcResult = mockMvc.perform(post("/api/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();
        String token = mvcResult.getResponse().getContentAsString();
        System.out.println(token);
        SellerProfileRequest sellerProfileRequest = new SellerProfileRequest("the best seller profile");
        mockMvc.perform(post("/api/profiles")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(sellerProfileRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approved").value(false));
    }


}
