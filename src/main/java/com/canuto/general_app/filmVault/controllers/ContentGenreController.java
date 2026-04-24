package com.canuto.general_app.filmVault.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.filmVault.dto.ContentGenreCreateRequest;
import com.canuto.general_app.filmVault.models.ContentGenre;
import com.canuto.general_app.filmVault.services.ContentGenreService;

@RestController
@RequestMapping("/filmvault/content-genres")
public class ContentGenreController {

    private final ContentGenreService contentGenreService;

    public ContentGenreController(ContentGenreService contentGenreService) {
        this.contentGenreService = contentGenreService;
    }

    @GetMapping
    public List<ContentGenre> getAll() {
        return contentGenreService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody ContentGenreCreateRequest request) {
        ContentGenre created = contentGenreService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "contentId", created.getId().getContentId(),
                "genreId", created.getId().getGenreId()));
    }

    @DeleteMapping("/{contentId}/{genreId}")
    public ResponseEntity<Void> delete(@PathVariable Long contentId, @PathVariable Long genreId) {
        contentGenreService.delete(contentId, genreId);
        return ResponseEntity.noContent().build();
    }
}
