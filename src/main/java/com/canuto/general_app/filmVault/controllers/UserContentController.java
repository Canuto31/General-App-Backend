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

import com.canuto.general_app.filmVault.dto.UserContentCreateRequest;
import com.canuto.general_app.filmVault.models.UserContent;
import com.canuto.general_app.filmVault.services.UserContentService;

@RestController
@RequestMapping("/filmvault/user-contents")
public class UserContentController {

    private final UserContentService userContentService;

    public UserContentController(UserContentService userContentService) {
        this.userContentService = userContentService;
    }

    @GetMapping
    public List<UserContent> getAll() {
        return userContentService.findAll();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody UserContentCreateRequest request) {
        UserContent created = userContentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", created.getId()));
    }

    @GetMapping("/{id}")
    public UserContent getById(@PathVariable Long id) {
        return userContentService.getById(id);
    }

    @PutMapping("/{id}")
    public UserContent update(@PathVariable Long id, @RequestBody UserContentCreateRequest request) {
        return userContentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userContentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
