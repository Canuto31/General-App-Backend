package com.canuto.general_app.filmVault.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.filmVault.dto.UserContentCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.models.User;
import com.canuto.general_app.filmVault.models.UserContent;
import com.canuto.general_app.filmVault.repositories.ContentRepository;
import com.canuto.general_app.filmVault.repositories.UserContentRepository;
import com.canuto.general_app.filmVault.repositories.UserRepository;

@Service
public class UserContentService {

    private final UserContentRepository userContentRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public UserContentService(UserContentRepository userContentRepository, UserRepository userRepository,
            ContentRepository contentRepository) {
        this.userContentRepository = userContentRepository;
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    public List<UserContent> findAll() {
        return userContentRepository.findAll();
    }

    public UserContent create(UserContentCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.userId()));
        Content content = contentRepository.findById(request.contentId())
                .orElseThrow(() -> new IllegalArgumentException("Content not found: " + request.contentId()));

        UserContent userContent = new UserContent();
        userContent.setUser(user);
        userContent.setContent(content);
        userContent.setStatus(request.status());
        userContent.setRating(request.rating());
        userContent.setNotes(request.notes());
        userContent.setAddedAt(request.addedAt());
        userContent.setWatchedAt(request.watchedAt());

        return userContentRepository.create(userContent);
    }

    public UserContent getById(Long id) {
        return userContentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserContent not found: " + id));
    }

    public UserContent update(Long id, UserContentCreateRequest request) {
        UserContent existing = getById(id);
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.userId()));
        Content content = contentRepository.findById(request.contentId())
                .orElseThrow(() -> new IllegalArgumentException("Content not found: " + request.contentId()));

        existing.setUser(user);
        existing.setContent(content);
        existing.setStatus(request.status());
        existing.setRating(request.rating());
        existing.setNotes(request.notes());
        existing.setAddedAt(request.addedAt());
        existing.setWatchedAt(request.watchedAt());

        return userContentRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!userContentRepository.existsById(id)) {
            throw new IllegalArgumentException("UserContent not found: " + id);
        }
        userContentRepository.deleteById(id);
    }
}
