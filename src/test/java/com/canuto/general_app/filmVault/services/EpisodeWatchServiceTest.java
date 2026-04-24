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

import com.canuto.general_app.filmVault.dto.EpisodeWatchCreateRequest;
import com.canuto.general_app.filmVault.models.Episode;
import com.canuto.general_app.filmVault.models.EpisodeWatch;
import com.canuto.general_app.filmVault.models.Season;
import com.canuto.general_app.filmVault.models.User;
import com.canuto.general_app.filmVault.repositories.EpisodeRepository;
import com.canuto.general_app.filmVault.repositories.EpisodeWatchRepository;
import com.canuto.general_app.filmVault.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class EpisodeWatchServiceTest {

    @Mock
    private EpisodeWatchRepository episodeWatchRepository;

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EpisodeWatchService episodeWatchService;

    @Test
    void create_shouldPersistEpisodeWatch() {
        User user = new User();
        user.setId(10L);

        Season season = new Season();
        season.setId(20L);

        Episode episode = new Episode();
        episode.setId(30L);
        episode.setSeason(season);

        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(episodeRepository.findById(30L)).thenReturn(Optional.of(episode));
        when(episodeWatchRepository.create(any(EpisodeWatch.class)))
                .thenAnswer(invocation -> {
                    EpisodeWatch episodeWatch = invocation.getArgument(0);
                    episodeWatch.setId(1L); // simulate @GeneratedValue
                    return episodeWatch;
                });

        LocalDateTime watchedAt = LocalDateTime.of(2026, 4, 16, 13, 30);
        EpisodeWatchCreateRequest request = new EpisodeWatchCreateRequest(10L, 30L, watchedAt);

        EpisodeWatch created = episodeWatchService.create(request);

        ArgumentCaptor<EpisodeWatch> captor = ArgumentCaptor.forClass(EpisodeWatch.class);
        verify(episodeWatchRepository).create(captor.capture());

        EpisodeWatch saved = captor.getValue();
        assertEquals(1L, saved.getId());
        assertEquals(user, saved.getUser());
        assertEquals(episode, saved.getEpisode());
        assertEquals(watchedAt, saved.getWatchedAt());
        assertEquals(created, saved);
    }
}

