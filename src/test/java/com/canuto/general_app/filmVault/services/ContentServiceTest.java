package com.canuto.general_app.filmVault.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.canuto.general_app.filmVault.dto.ContentCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.models.User;
import com.canuto.general_app.filmVault.repositories.ContentRepository;
import com.canuto.general_app.filmVault.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class ContentServiceTest {

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ContentService contentService;

    @Test
    void create_shouldPersistContent() {
        User user = new User();
        user.setId(10L);

        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(contentRepository.create(any(Content.class))).thenAnswer(invocation -> {
            Content content = invocation.getArgument(0);
            content.setId(1L); // simulate @GeneratedValue behavior
            return content;
        });

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

        Content created = contentService.create(request);

        ArgumentCaptor<Content> captor = ArgumentCaptor.forClass(Content.class);
        verify(contentRepository).create(captor.capture());

        Content saved = captor.getValue();
        assertEquals(1L, saved.getId());
        assertEquals("Test Title", saved.getTitle());
        assertEquals(user, saved.getCreatedByUser());

        assertEquals(created, saved);
    }
}

