package com.canuto.general_app.filmVault.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.filmVault.dto.EpisodeWatchCreateRequest;
import com.canuto.general_app.filmVault.models.Episode;
import com.canuto.general_app.filmVault.models.EpisodeWatch;
import com.canuto.general_app.filmVault.models.User;
import com.canuto.general_app.filmVault.repositories.EpisodeRepository;
import com.canuto.general_app.filmVault.repositories.EpisodeWatchRepository;
import com.canuto.general_app.filmVault.repositories.UserRepository;

@Service
public class EpisodeWatchService {

    private final EpisodeWatchRepository episodeWatchRepository;
    private final EpisodeRepository episodeRepository;
    private final UserRepository userRepository;

    public EpisodeWatchService(EpisodeWatchRepository episodeWatchRepository, EpisodeRepository episodeRepository,
            UserRepository userRepository) {
        this.episodeWatchRepository = episodeWatchRepository;
        this.episodeRepository = episodeRepository;
        this.userRepository = userRepository;
    }

    public List<EpisodeWatch> findAll() {
        return episodeWatchRepository.findAll();
    }

    public EpisodeWatch create(EpisodeWatchCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.userId()));
        Episode episode = episodeRepository.findById(request.episodeId())
                .orElseThrow(() -> new IllegalArgumentException("Episode not found: " + request.episodeId()));

        EpisodeWatch episodeWatch = new EpisodeWatch();
        episodeWatch.setUser(user);
        episodeWatch.setEpisode(episode);
        episodeWatch.setWatchedAt(request.watchedAt());

        return episodeWatchRepository.create(episodeWatch);
    }

    public EpisodeWatch getById(Long id) {
        return episodeWatchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("EpisodeWatch not found: " + id));
    }

    public EpisodeWatch update(Long id, EpisodeWatchCreateRequest request) {
        EpisodeWatch existing = getById(id);
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.userId()));
        Episode episode = episodeRepository.findById(request.episodeId())
                .orElseThrow(() -> new IllegalArgumentException("Episode not found: " + request.episodeId()));

        existing.setUser(user);
        existing.setEpisode(episode);
        existing.setWatchedAt(request.watchedAt());

        return episodeWatchRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!episodeWatchRepository.existsById(id)) {
            throw new IllegalArgumentException("EpisodeWatch not found: " + id);
        }
        episodeWatchRepository.deleteById(id);
    }
}
