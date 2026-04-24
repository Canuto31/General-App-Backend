package com.canuto.general_app.filmVault.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.canuto.general_app.filmVault.dto.ContentGenreCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.models.ContentGenre;
import com.canuto.general_app.filmVault.models.Genre;
import com.canuto.general_app.filmVault.repositories.ContentGenreRepository;
import com.canuto.general_app.filmVault.repositories.ContentRepository;
import com.canuto.general_app.filmVault.repositories.GenreRepository;

@ExtendWith(MockitoExtension.class)
class ContentGenreServiceTest {

    @Mock
    private ContentGenreRepository contentGenreRepository;

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private ContentGenreService contentGenreService;

    @Test
    void create_shouldPersistContentGenre() {
        Content content = new Content();
        content.setId(1L);

        Genre genre = new Genre();
        genre.setId(2L);

        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));
        when(genreRepository.findById(2L)).thenReturn(Optional.of(genre));
        when(contentGenreRepository.create(any(ContentGenre.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ContentGenreCreateRequest request = new ContentGenreCreateRequest(1L, 2L);

        ContentGenre created = contentGenreService.create(request);

        ArgumentCaptor<ContentGenre> captor = ArgumentCaptor.forClass(ContentGenre.class);
        verify(contentGenreRepository).create(captor.capture());

        ContentGenre saved = captor.getValue();
        assertEquals(1L, saved.getId().getContentId());
        assertEquals(2L, saved.getId().getGenreId());
        assertEquals(content, saved.getContent());
        assertEquals(genre, saved.getGenre());
        assertEquals(created, saved);
    }
}

