package com.canuto.general_app.filmVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canuto.general_app.filmVault.models.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    
    default Episode create(Episode episode) {
        return save(episode);
    }
}
