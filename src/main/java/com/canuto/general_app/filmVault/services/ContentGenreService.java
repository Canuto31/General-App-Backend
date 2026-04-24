package com.canuto.general_app.filmVault.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.filmVault.dto.ContentGenreCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.models.ContentGenre;
import com.canuto.general_app.filmVault.models.ContentGenreId;
import com.canuto.general_app.filmVault.models.Genre;
import com.canuto.general_app.filmVault.repositories.ContentGenreRepository;
import com.canuto.general_app.filmVault.repositories.ContentRepository;
import com.canuto.general_app.filmVault.repositories.GenreRepository;

@Service
public class ContentGenreService {

    private final ContentGenreRepository contentGenreRepository;
    private final ContentRepository contentRepository;
    private final GenreRepository genreRepository;

    public ContentGenreService(ContentGenreRepository contentGenreRepository, ContentRepository contentRepository,
            GenreRepository genreRepository) {
        this.contentGenreRepository = contentGenreRepository;
        this.contentRepository = contentRepository;
        this.genreRepository = genreRepository;
    }

    public List<ContentGenre> findAll() {
        return contentGenreRepository.findAll();
    }

    public ContentGenre create(ContentGenreCreateRequest request) {
        Content content = contentRepository.findById(request.contentId())
                .orElseThrow(() -> new IllegalArgumentException("Content not found: " + request.contentId()));
        Genre genre = genreRepository.findById(request.genreId())
                .orElseThrow(() -> new IllegalArgumentException("Genre not found: " + request.genreId()));

        ContentGenre contentGenre = new ContentGenre(content, genre);
        return contentGenreRepository.create(contentGenre);
    }

    public void delete(Long contentId, Long genreId) {
        ContentGenreId id = new ContentGenreId(contentId, genreId);
        ContentGenre existing = contentGenreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ContentGenre not found: " + contentId + " / " + genreId));
        contentGenreRepository.deleteById(existing.getId());
    }
}
