package com.canuto.general_app.filmVault.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.filmVault.dto.ContentCreateRequest;
import com.canuto.general_app.filmVault.models.Content;
import com.canuto.general_app.filmVault.services.ContentService;

@RestController
@RequestMapping("/filmvault/contents")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public List<Content> getAll() {
        return contentService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody ContentCreateRequest request) {
        Content created = contentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", created.getId()));
    }

    @GetMapping("/{id}")
    public Content getById(@PathVariable Long id) {
        return contentService.getById(id);
    }

    @PutMapping("/{id}")
    public Content update(@PathVariable Long id, @RequestBody ContentCreateRequest request) {
        return contentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        contentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
