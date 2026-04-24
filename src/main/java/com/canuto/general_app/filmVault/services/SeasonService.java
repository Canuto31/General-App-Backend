package com.canuto.general_app.filmVault.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.filmVault.dto.SeasonCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.models.Season;
import com.canuto.general_app.filmVault.repositories.ContentRepository;
import com.canuto.general_app.filmVault.repositories.SeasonRepository;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final ContentRepository contentRepository;

    public SeasonService(SeasonRepository seasonRepository, ContentRepository contentRepository) {
        this.seasonRepository = seasonRepository;
        this.contentRepository = contentRepository;
    }

    public List<Season> findAll() {
        return seasonRepository.findAll();
    }

    public Season create(SeasonCreateRequest request) {
        Content content = contentRepository.findById(request.contentId())
                .orElseThrow(() -> new IllegalArgumentException("Content not found: " + request.contentId()));

        Season season = new Season();
        season.setContent(content);
        season.setSeasonNumber(request.seasonNumber());
        season.setReleaseYear(request.releaseYear());

        return seasonRepository.create(season);
    }

    public Season getById(Long id) {
        return seasonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Season not found: " + id));
    }

    public Season update(Long id, SeasonCreateRequest request) {
        Season existing = getById(id);
        Content content = contentRepository.findById(request.contentId())
                .orElseThrow(() -> new IllegalArgumentException("Content not found: " + request.contentId()));

        existing.setContent(content);
        existing.setSeasonNumber(request.seasonNumber());
        existing.setReleaseYear(request.releaseYear());

        return seasonRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!seasonRepository.existsById(id)) {
            throw new IllegalArgumentException("Season not found: " + id);
        }
        seasonRepository.deleteById(id);
    }
}
