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

import com.canuto.general_app.filmVault.dto.PlatformCreateRequest;
import com.canuto.general_app.filmVault.models.Platform;
import com.canuto.general_app.filmVault.repositories.PlatformRepository;

@ExtendWith(MockitoExtension.class)
class PlatformServiceTest {

    @Mock
    private PlatformRepository platformRepository;

    @InjectMocks
    private PlatformService platformService;

    @Test
    void create_shouldPersistPlatform() {
        when(platformRepository.create(any(Platform.class))).thenAnswer(invocation -> {
            Platform platform = invocation.getArgument(0);
            platform.setId(7L); // simulate @GeneratedValue
            return platform;
        });

        PlatformCreateRequest request = new PlatformCreateRequest("Netflix", "netflix.png");

        Platform created = platformService.create(request);

        ArgumentCaptor<Platform> captor = ArgumentCaptor.forClass(Platform.class);
        verify(platformRepository).create(captor.capture());

        Platform saved = captor.getValue();
        assertEquals(7L, saved.getId());
        assertEquals("Netflix", saved.getName());
        assertEquals("netflix.png", saved.getLogoUrl());
        assertEquals(created, saved);
    }
}

