package com.canuto.general_app.filmVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canuto.general_app.filmVault.models.Season;

public interface SeasonRepository extends JpaRepository<Season, Long> {
    
    default Season create(Season season) {
        return save(season);
    }
}
