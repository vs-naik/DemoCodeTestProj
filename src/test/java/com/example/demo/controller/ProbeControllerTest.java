package com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProbeController.class)
public class ProbeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testValidCommandSequence() throws Exception {
        Map<String, String> request = Map.of("commands", "FFRFF");

        mockMvc.perform(post("/probe/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.position").exists())
                .andExpect(jsonPath("$.direction").exists())
                .andExpect(jsonPath("$.visited").isArray());
    }
    
    @Test
    void testEmptyCommand() throws Exception {
        Map<String, String> request = Map.of("commands", "");

        mockMvc.perform(post("/probe/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Commands must not be empty"));
    }


    @Test
    void testInvalidCommandCharacter() throws Exception {
        Map<String, String> request = Map.of("commands", "FXR");

        mockMvc.perform(post("/probe/command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}
