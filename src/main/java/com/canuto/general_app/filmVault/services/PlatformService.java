package com.canuto.general_app.filmVault.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.filmVault.dto.PlatformCreateRequest;
import com.canuto.general_app.filmVault.models.Platform;
import com.canuto.general_app.filmVault.repositories.PlatformRepository;

@Service
public class PlatformService {

    private final PlatformRepository platformRepository;

    public PlatformService(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    public List<Platform> findAll() {
        return platformRepository.findAll();
    }

    public Platform create(PlatformCreateRequest request) {
        Platform platform = new Platform();
        platform.setName(request.name());
        platform.setLogoUrl(request.logoUrl());
        return platformRepository.create(platform);
    }

    public Platform getById(Long id) {
        return platformRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Platform not found: " + id));
    }

    public Platform update(Long id, PlatformCreateRequest request) {
        Platform existing = getById(id);
        existing.setName(request.name());
        existing.setLogoUrl(request.logoUrl());
        return platformRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!platformRepository.existsById(id)) {
            throw new IllegalArgumentException("Platform not found: " + id);
        }
        platformRepository.deleteById(id);
    }
}
