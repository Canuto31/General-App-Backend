package com.canuto.general_app.filmVault.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.canuto.general_app.filmVault.dto.GenreCreateRequest;
import com.canuto.general_app.filmVault.models.Genre;
import com.canuto.general_app.filmVault.repositories.GenreRepository;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @Test
    void create_shouldPersistGenre() {
        when(genreRepository.create(any(Genre.class))).thenAnswer(invocation -> {
            Genre genre = invocation.getArgument(0);
            genre.setId(5L); // simulate @GeneratedValue
            return genre;
        });

        GenreCreateRequest request = new GenreCreateRequest("Action");

        Genre created = genreService.create(request);

        ArgumentCaptor<Genre> captor = ArgumentCaptor.forClass(Genre.class);
        verify(genreRepository).create(captor.capture());

        Genre saved = captor.getValue();
        assertEquals(5L, saved.getId());
        assertEquals("Action", saved.getName());
        assertEquals(created, saved);
    }
}

