package com.canuto.general_app.filmVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canuto.general_app.filmVault.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    
    default Genre create(Genre genre) {
        return save(genre);
    }
}
