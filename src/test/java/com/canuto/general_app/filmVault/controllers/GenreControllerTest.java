package com.canuto.general_app.filmVault.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.canuto.general_app.filmVault.models.Genre;
import com.canuto.general_app.filmVault.services.GenreService;

@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GenreService genreService;

    @Test
    void getAll_shouldReturnGenres() throws Exception {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Action");

        when(genreService.findAll()).thenReturn(List.of(genre));

        mockMvc.perform(get("/filmvault/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Action"));
    }
}
