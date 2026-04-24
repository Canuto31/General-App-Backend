package com.canuto.general_app.filmVault.dto;

import java.time.LocalDateTime;

public record EpisodeWatchCreateRequest(
        Long userId,
        Long episodeId,
        LocalDateTime watchedAt) {
}

