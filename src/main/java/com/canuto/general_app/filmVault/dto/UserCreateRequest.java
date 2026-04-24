package com.canuto.general_app.filmVault.dto;

import java.time.LocalDateTime;

public record UserCreateRequest(
        String userName,
        String email,
        String passwordHash,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}

