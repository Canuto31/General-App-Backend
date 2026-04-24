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

import com.canuto.general_app.filmVault.dto.PlatformCreateRequest;
import com.canuto.general_app.filmVault.models.Platform;
import com.canuto.general_app.filmVault.services.PlatformService;

@RestController
@RequestMapping("/filmvault/platforms")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public List<Platform> getAll() {
        return platformService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody PlatformCreateRequest request) {
        Platform created = platformService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", created.getId()));
    }

    @GetMapping("/{id}")
    public Platform getById(@PathVariable Long id) {
        return platformService.getById(id);
    }

    @PutMapping("/{id}")
    public Platform update(@PathVariable Long id, @RequestBody PlatformCreateRequest request) {
        return platformService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        platformService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
