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

import com.canuto.general_app.filmVault.dto.EpisodeCreateRequest;
import com.canuto.general_app.filmVault.models.Episode;
import com.canuto.general_app.filmVault.models.Season;
import com.canuto.general_app.filmVault.repositories.EpisodeRepository;
import com.canuto.general_app.filmVault.repositories.SeasonRepository;

@ExtendWith(MockitoExtension.class)
class EpisodeServiceTest {

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private SeasonRepository seasonRepository;

    @InjectMocks
    private EpisodeService episodeService;

    @Test
    void create_shouldPersistEpisode() {
        Season season = new Season();
        season.setId(300L);

        when(seasonRepository.findById(300L)).thenReturn(Optional.of(season));
        when(episodeRepository.create(any(Episode.class))).thenAnswer(invocation -> {
            Episode episode = invocation.getArgument(0);
            episode.setId(400L); // simulate @GeneratedValue
            return episode;
        });

        EpisodeCreateRequest request = new EpisodeCreateRequest(300L, 5, "Episode 5", 45);

        Episode created = episodeService.create(request);

        ArgumentCaptor<Episode> captor = ArgumentCaptor.forClass(Episode.class);
        verify(episodeRepository).create(captor.capture());

        Episode saved = captor.getValue();
        assertEquals(400L, saved.getId());
        assertEquals(5, saved.getEpisodeNumber());
        assertEquals("Episode 5", saved.getTitle());
        assertEquals(season, saved.getSeason());
        assertEquals(created, saved);
    }
}

