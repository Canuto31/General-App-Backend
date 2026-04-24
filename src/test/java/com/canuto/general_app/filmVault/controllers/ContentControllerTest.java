package com.canuto.general_app.filmVault.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.canuto.general_app.filmVault.dto.ContentCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.services.ContentService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ContentController.class)
class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ContentService contentService;

    @Test
    void create_shouldReturnCreatedId() throws Exception {
        Content created = new Content();
        created.setId(1L);

        when(contentService.create(any(ContentCreateRequest.class))).thenReturn(created);

        LocalDateTime now = LocalDateTime.of(2026, 4, 16, 12, 0);
        ContentCreateRequest request = new ContentCreateRequest(
                "imdb",
                123L,
                "Test Title",
                "Test Description",
                2020,
                120,
                "MOVIE",
                "poster.jpg",
                10L,
                now,
                null);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/filmvault/contents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }
}

