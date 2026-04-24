package com.canuto.general_app.filmVault.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.filmVault.dto.EpisodeWatchCreateRequest;
import com.canuto.general_app.filmVault.models.EpisodeWatch;
import com.canuto.general_app.filmVault.services.EpisodeWatchService;

@RestController
@RequestMapping("/filmvault/episode-watches")
public class EpisodeWatchController {

    private final EpisodeWatchService episodeWatchService;

    public EpisodeWatchController(EpisodeWatchService episodeWatchService) {
        this.episodeWatchService = episodeWatchService;
    }

    @GetMapping
    public List<EpisodeWatch> getAll() {
        return episodeWatchService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody EpisodeWatchCreateRequest request) {
        EpisodeWatch created = episodeWatchService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", created.getId()));
    }

    @GetMapping("/{id}")
    public EpisodeWatch getById(@PathVariable Long id) {
        return episodeWatchService.getById(id);
    }

    @PutMapping("/{id}")
    public EpisodeWatch update(@PathVariable Long id, @RequestBody EpisodeWatchCreateRequest request) {
        return episodeWatchService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        episodeWatchService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
