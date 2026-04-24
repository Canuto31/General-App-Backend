package com.canuto.general_app.filmVault.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.filmVault.dto.EpisodeCreateRequest;
import com.canuto.general_app.filmVault.models.Episode;
import com.canuto.general_app.filmVault.models.Season;
import com.canuto.general_app.filmVault.repositories.EpisodeRepository;
import com.canuto.general_app.filmVault.repositories.SeasonRepository;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final SeasonRepository seasonRepository;

    public EpisodeService(EpisodeRepository episodeRepository, SeasonRepository seasonRepository) {
        this.episodeRepository = episodeRepository;
        this.seasonRepository = seasonRepository;
    }

    public List<Episode> findAll() {
        return episodeRepository.findAll();
    }

    public Episode create(EpisodeCreateRequest request) {
        Season season = seasonRepository.findById(request.seasonId())
                .orElseThrow(() -> new IllegalArgumentException("Season not found: " + request.seasonId()));

        Episode episode = new Episode();
        episode.setSeason(season);
        episode.setEpisodeNumber(request.episodeNumber());
        episode.setTitle(request.title());
        episode.setDurationMinutes(request.durationMinutes());

        return episodeRepository.create(episode);
    }

    public Episode getById(Long id) {
        return episodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Episode not found: " + id));
    }

    public Episode update(Long id, EpisodeCreateRequest request) {
        Episode existing = getById(id);
        Season season = seasonRepository.findById(request.seasonId())
                .orElseThrow(() -> new IllegalArgumentException("Season not found: " + request.seasonId()));

        existing.setSeason(season);
        existing.setEpisodeNumber(request.episodeNumber());
        existing.setTitle(request.title());
        existing.setDurationMinutes(request.durationMinutes());
        return episodeRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!episodeRepository.existsById(id)) {
            throw new IllegalArgumentException("Episode not found: " + id);
        }
        episodeRepository.deleteById(id);
    }
}
