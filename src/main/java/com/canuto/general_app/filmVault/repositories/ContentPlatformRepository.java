package com.canuto.general_app.filmVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canuto.general_app.filmVault.models.ContentPlatform;
import com.canuto.general_app.filmVault.models.ContentPlatformId;

public interface ContentPlatformRepository extends JpaRepository<ContentPlatform, ContentPlatformId> {
    
    default ContentPlatform create(ContentPlatform contentPlatform) {
        return save(contentPlatform);
    }
}
