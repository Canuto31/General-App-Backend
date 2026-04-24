package com.canuto.general_app.filmVault.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.canuto.general_app.filmVault.dto.UserContentCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.models.User;
import com.canuto.general_app.filmVault.models.UserContent;
import com.canuto.general_app.filmVault.repositories.ContentRepository;
import com.canuto.general_app.filmVault.repositories.UserContentRepository;
import com.canuto.general_app.filmVault.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserContentServiceTest {

    @Mock
    private UserContentRepository userContentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private UserContentService userContentService;

    @Test
    void create_shouldPersistUserContent() {
        User user = new User();
        user.setId(10L);

        Content content = new Content();
        content.setId(11L);

        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(contentRepository.findById(11L)).thenReturn(Optional.of(content));
        when(userContentRepository.create(any(UserContent.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LocalDateTime addedAt = LocalDateTime.of(2026, 4, 16, 14, 0);
        LocalDateTime watchedAt = LocalDateTime.of(2026, 4, 16, 15, 0);
        UserContentCreateRequest request = new UserContentCreateRequest(
                10L,
                11L,
                "WATCHING",
                new BigDecimal("4.5"),
                "Great",
                addedAt,
                watchedAt);

        UserContent created = userContentService.create(request);

        ArgumentCaptor<UserContent> captor = ArgumentCaptor.forClass(UserContent.class);
        verify(userContentRepository).create(captor.capture());

        UserContent saved = captor.getValue();
        assertEquals(user, saved.getUser());
        assertEquals(content, saved.getContent());
        assertEquals("WATCHING", saved.getStatus());
        assertEquals(new BigDecimal("4.5"), saved.getRating());
        assertEquals("Great", saved.getNotes());
        assertEquals(addedAt, saved.getAddedAt());
        assertEquals(watchedAt, saved.getWatchedAt());
        assertEquals(created, saved);
    }
}

