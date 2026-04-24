package com.canuto.general_app.filmVault.dto;

import java.time.LocalDateTime;

public record ContentCreateRequest(
        String source,
        Long tmdbId,
        String title,
        String description,
        Integer releaseYear,
        Integer durationMinutes,
        String type,
        String posterUrl,
        Long createdByUserId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}

