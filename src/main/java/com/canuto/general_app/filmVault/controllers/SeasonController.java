package com.canuto.general_app.filmVault.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.filmVault.dto.SeasonCreateRequest;
import com.canuto.general_app.filmVault.models.Season;
import com.canuto.general_app.filmVault.services.SeasonService;

@RestController
@RequestMapping("/filmvault/seasons")
public class SeasonController {

    private final SeasonService seasonService;

    public SeasonController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @GetMapping
    public List<Season> getAll() {
        return seasonService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody SeasonCreateRequest request) {
        Season created = seasonService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", created.getId()));
    }

    @GetMapping("/{id}")
    public Season getById(@PathVariable Long id) {
        return seasonService.getById(id);
    }

    @PutMapping("/{id}")
    public Season update(@PathVariable Long id, @RequestBody SeasonCreateRequest request) {
        return seasonService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        seasonService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
