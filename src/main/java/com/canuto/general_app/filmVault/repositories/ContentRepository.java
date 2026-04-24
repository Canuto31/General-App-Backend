package com.canuto.general_app.filmVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canuto.general_app.filmVault.models.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {
    
    default Content create(Content content) {
        return save(content);
    }
}
