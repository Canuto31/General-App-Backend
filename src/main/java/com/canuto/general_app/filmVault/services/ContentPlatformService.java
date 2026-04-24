package com.canuto.general_app.filmVault.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.filmVault.dto.ContentPlatformCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.models.ContentPlatform;
import com.canuto.general_app.filmVault.models.ContentPlatformId;
import com.canuto.general_app.filmVault.models.Platform;
import com.canuto.general_app.filmVault.repositories.ContentPlatformRepository;
import com.canuto.general_app.filmVault.repositories.ContentRepository;
import com.canuto.general_app.filmVault.repositories.PlatformRepository;

@Service
public class ContentPlatformService {

    private final ContentPlatformRepository contentPlatformRepository;
    private final ContentRepository contentRepository;
    private final PlatformRepository platformRepository;

    public ContentPlatformService(ContentPlatformRepository contentPlatformRepository,
            ContentRepository contentRepository, PlatformRepository platformRepository) {
        this.contentPlatformRepository = contentPlatformRepository;
        this.contentRepository = contentRepository;
        this.platformRepository = platformRepository;
    }

    public List<ContentPlatform> findAll() {
        return contentPlatformRepository.findAll();
    }

    public ContentPlatform create(ContentPlatformCreateRequest request) {
        Content content = contentRepository.findById(request.contentId())
                .orElseThrow(() -> new IllegalArgumentException("Content not found: " + request.contentId()));
        Platform platform = platformRepository.findById(request.platformId())
                .orElseThrow(() -> new IllegalArgumentException("Platform not found: " + request.platformId()));

        ContentPlatform contentPlatform = new ContentPlatform(content, platform);
        return contentPlatformRepository.create(contentPlatform);
    }

    public void delete(Long contentId, Long platformId) {
        ContentPlatformId id = new ContentPlatformId(contentId, platformId);
        ContentPlatform existing = contentPlatformRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "ContentPlatform not found: " + contentId + " / " + platformId));
        contentPlatformRepository.deleteById(existing.getId());
    }
}
