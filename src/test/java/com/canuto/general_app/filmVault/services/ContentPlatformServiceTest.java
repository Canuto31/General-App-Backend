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

import com.canuto.general_app.filmVault.dto.ContentPlatformCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.models.ContentPlatform;
import com.canuto.general_app.filmVault.models.Platform;
import com.canuto.general_app.filmVault.repositories.ContentPlatformRepository;
import com.canuto.general_app.filmVault.repositories.ContentRepository;
import com.canuto.general_app.filmVault.repositories.PlatformRepository;

@ExtendWith(MockitoExtension.class)
class ContentPlatformServiceTest {

    @Mock
    private ContentPlatformRepository contentPlatformRepository;

    @Mock
    private ContentRepository contentRepository;

    @Mock
    private PlatformRepository platformRepository;

    @InjectMocks
    private ContentPlatformService contentPlatformService;

    @Test
    void create_shouldPersistContentPlatform() {
        Content content = new Content();
        content.setId(1L);

        Platform platform = new Platform();
        platform.setId(3L);

        when(contentRepository.findById(1L)).thenReturn(Optional.of(content));
        when(platformRepository.findById(3L)).thenReturn(Optional.of(platform));
        when(contentPlatformRepository.create(any(ContentPlatform.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ContentPlatformCreateRequest request = new ContentPlatformCreateRequest(1L, 3L);

        ContentPlatform created = contentPlatformService.create(request);

        ArgumentCaptor<ContentPlatform> captor = ArgumentCaptor.forClass(ContentPlatform.class);
        verify(contentPlatformRepository).create(captor.capture());

        ContentPlatform saved = captor.getValue();
        assertEquals(1L, saved.getId().getContentId());
        assertEquals(3L, saved.getId().getPlatformId());
        assertEquals(content, saved.getContent());
        assertEquals(platform, saved.getPlatform());
        assertEquals(created, saved);
    }
}

