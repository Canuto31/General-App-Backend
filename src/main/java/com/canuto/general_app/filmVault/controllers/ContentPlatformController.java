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
import org.springframework.web.bind.annotation.RestController;

import com.canuto.general_app.filmVault.dto.ContentPlatformCreateRequest;
import com.canuto.general_app.filmVault.models.ContentPlatform;
import com.canuto.general_app.filmVault.services.ContentPlatformService;

@RestController
@RequestMapping("/filmvault/content-platforms")
public class ContentPlatformController {

    private final ContentPlatformService contentPlatformService;

    public ContentPlatformController(ContentPlatformService contentPlatformService) {
        this.contentPlatformService = contentPlatformService;
    }

    @GetMapping
    public List<ContentPlatform> getAll() {
        return contentPlatformService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody ContentPlatformCreateRequest request) {
        ContentPlatform created = contentPlatformService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "contentId", created.getId().getContentId(),
                "platformId", created.getId().getPlatformId()));
    }

    @DeleteMapping("/{contentId}/{platformId}")
    public ResponseEntity<Void> delete(@PathVariable Long contentId, @PathVariable Long platformId) {
        contentPlatformService.delete(contentId, platformId);
        return ResponseEntity.noContent().build();
    }
}
