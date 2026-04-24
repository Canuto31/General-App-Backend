package com.canuto.general_app.filmVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canuto.general_app.filmVault.models.Platform;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    
    default Platform create(Platform platform) {
        return save(platform);
    }
}
