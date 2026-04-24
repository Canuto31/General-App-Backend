package com.canuto.general_app.filmVault.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.filmVault.dto.EpisodeCreateRequest;
import com.canuto.general_app.filmVault.models.Episode;
import com.canuto.general_app.filmVault.services.EpisodeService;

@RestController
@RequestMapping("/filmvault/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping
    public List<Episode> getAll() {
        return episodeService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody EpisodeCreateRequest request) {
        Episode created = episodeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", created.getId()));
    }

    @GetMapping("/{id}")
    public Episode getById(@PathVariable Long id) {
        return episodeService.getById(id);
    }

    @PutMapping("/{id}")
    public Episode update(@PathVariable Long id, @RequestBody EpisodeCreateRequest request) {
        return episodeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        episodeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
