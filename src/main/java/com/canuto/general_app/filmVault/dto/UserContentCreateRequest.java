package com.canuto.general_app.filmVault.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserContentCreateRequest(
        Long userId,
        Long contentId,
        String status,
        BigDecimal rating,
        String notes,
        LocalDateTime addedAt,
        LocalDateTime watchedAt) {
}

