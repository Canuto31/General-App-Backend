package com.canuto.general_app.filmVault.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.filmVault.dto.GenreCreateRequest;
import com.canuto.general_app.filmVault.models.Genre;
import com.canuto.general_app.filmVault.repositories.GenreRepository;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public Genre create(GenreCreateRequest request) {
        Genre genre = new Genre();
        genre.setName(request.name());
        return genreRepository.create(genre);
    }

    public Genre getById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Genre not found: " + id));
    }

    public Genre update(Long id, GenreCreateRequest request) {
        Genre existing = getById(id);
        existing.setName(request.name());
        return genreRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new IllegalArgumentException("Genre not found: " + id);
        }
        genreRepository.deleteById(id);
    }
}
