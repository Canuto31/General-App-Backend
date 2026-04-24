package com.canuto.general_app.filmVault.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.filmVault.dto.ContentCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.models.User;
import com.canuto.general_app.filmVault.repositories.ContentRepository;
import com.canuto.general_app.filmVault.repositories.UserRepository;

@Service
public class ContentService {

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

    public ContentService(ContentRepository contentRepository, UserRepository userRepository) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
    }

    public List<Content> findAll() {
        return contentRepository.findAll();
    }

    public Content create(ContentCreateRequest request) {
        User createdByUser = userRepository.findById(request.createdByUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.createdByUserId()));

        Content content = new Content();
        content.setSource(request.source());
        content.setTmdbId(request.tmdbId());
        content.setTitle(request.title());
        content.setDescription(request.description());
        content.setReleaseYear(request.releaseYear());
        content.setDurationMinutes(request.durationMinutes());
        content.setType(request.type());
        content.setPosterUrl(request.posterUrl());
        content.setCreatedByUser(createdByUser);
        content.setCreatedAt(request.createdAt());
        content.setUpdatedAt(request.updatedAt());

        return contentRepository.create(content);
    }

    public Content getById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Content not found: " + id));
    }

    public Content update(Long id, ContentCreateRequest request) {
        Content existing = getById(id);
        User createdByUser = userRepository.findById(request.createdByUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.createdByUserId()));

        existing.setSource(request.source());
        existing.setTmdbId(request.tmdbId());
        existing.setTitle(request.title());
        existing.setDescription(request.description());
        existing.setReleaseYear(request.releaseYear());
        existing.setDurationMinutes(request.durationMinutes());
        existing.setType(request.type());
        existing.setPosterUrl(request.posterUrl());
        existing.setCreatedByUser(createdByUser);
        existing.setCreatedAt(request.createdAt());
        existing.setUpdatedAt(request.updatedAt());

        return contentRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!contentRepository.existsById(id)) {
            throw new IllegalArgumentException("Content not found: " + id);
        }
        contentRepository.deleteById(id);
    }
}
