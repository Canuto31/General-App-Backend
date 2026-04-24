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

import com.canuto.general_app.filmVault.dto.SeasonCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.models.Season;
import com.canuto.general_app.filmVault.repositories.ContentRepository;
import com.canuto.general_app.filmVault.repositories.SeasonRepository;

@ExtendWith(MockitoExtension.class)
class SeasonServiceTest {

    @Mock
    private SeasonRepository seasonRepository;

    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private SeasonService seasonService;

    @Test
    void create_shouldPersistSeason() {
        Content content = new Content();
        content.setId(100L);

        when(contentRepository.findById(100L)).thenReturn(Optional.of(content));
        when(seasonRepository.create(any(Season.class))).thenAnswer(invocation -> {
            Season season = invocation.getArgument(0);
            season.setId(200L); // simulate @GeneratedValue
            return season;
        });

        SeasonCreateRequest request = new SeasonCreateRequest(100L, 1, 2022);

        Season created = seasonService.create(request);

        ArgumentCaptor<Season> captor = ArgumentCaptor.forClass(Season.class);
        verify(seasonRepository).create(captor.capture());

        Season saved = captor.getValue();
        assertEquals(200L, saved.getId());
        assertEquals(1, saved.getSeasonNumber());
        assertEquals(content, saved.getContent());
        assertEquals(created, saved);
    }
}

