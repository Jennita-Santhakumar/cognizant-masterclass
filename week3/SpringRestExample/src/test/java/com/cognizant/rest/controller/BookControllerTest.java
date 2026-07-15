package com.cognizant.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Proves the Week 3 deliverable end-to-end: sending bad input returns a
 * structured 400, and a missing resource returns a structured 404 -- both
 * via GlobalExceptionHandler, not a raw stack trace / 500.
 */
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createWithBlankTitleReturns400WithValidationDetails() throws Exception {
        String badPayload = """
                {"title": "", "author": "Someone", "publicationYear": 2020}
                """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badPayload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.details").isArray());
    }

    @Test
    void getMissingBookReturns404WithMessage() throws Exception {
        mockMvc.perform(get("/api/books/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("No book with id 999999"));
    }

    @Test
    void createValidBookReturns201() throws Exception {
        String goodPayload = """
                {"title": "Things Fall Apart", "author": "Chinua Achebe", "publicationYear": 1958}
                """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(goodPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Things Fall Apart"));
    }
}
