package com.canuto.general_app.filmVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canuto.general_app.filmVault.models.EpisodeWatch;

public interface EpisodeWatchRepository extends JpaRepository<EpisodeWatch, Long> {
    
    default EpisodeWatch create(EpisodeWatch episodeWatch) {
        return save(episodeWatch);
    }
}
