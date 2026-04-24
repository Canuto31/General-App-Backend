package com.canuto.general_app.filmVault.dto;

public record SeasonCreateRequest(
        Long contentId,
        Integer seasonNumber,
        Integer releaseYear) {
}

