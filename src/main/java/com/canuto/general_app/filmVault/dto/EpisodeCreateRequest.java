package com.canuto.general_app.filmVault.dto;

public record EpisodeCreateRequest(
        Long seasonId,
        Integer episodeNumber,
        String title,
        Integer durationMinutes) {
}

