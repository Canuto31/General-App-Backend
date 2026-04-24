package com.canuto.general_app.filmVault.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.filmVault.dto.GenreCreateRequest;
import com.canuto.general_app.filmVault.models.Genre;
import com.canuto.general_app.filmVault.services.GenreService;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/filmvault/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> getAll() {
        return genreService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody GenreCreateRequest request) {
        Genre created = genreService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", created.getId()));
    }

    @GetMapping("/{id}")
    public Genre getById(@PathVariable Long id) {
        return genreService.getById(id);
    }

    @PutMapping("/{id}")
    public Genre update(@PathVariable Long id, @RequestBody GenreCreateRequest request) {
        return genreService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
