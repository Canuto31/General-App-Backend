package com.canuto.general_app.filmVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canuto.general_app.filmVault.models.ContentGenre;
import com.canuto.general_app.filmVault.models.ContentGenreId;

public interface ContentGenreRepository extends JpaRepository<ContentGenre, ContentGenreId> {
    
    default ContentGenre create(ContentGenre contentGenre) {
        return save(contentGenre);
    }
}
