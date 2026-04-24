package com.canuto.general_app.filmVault.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.canuto.general_app.filmVault.dto.UserCreateRequest;
import com.canuto.general_app.filmVault.models.User;
import com.canuto.general_app.filmVault.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(UserCreateRequest request) {
        User user = new User();
        user.setUserName(request.userName());
        user.setEmail(request.email());
        user.setPasswordHash(request.passwordHash());
        user.setCreatedAt(request.createdAt());
        user.setUpdatedAt(request.updatedAt());
        return userRepository.create(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    public User update(Long id, UserCreateRequest request) {
        User existing = getById(id);
        existing.setUserName(request.userName());
        existing.setEmail(request.email());
        existing.setPasswordHash(request.passwordHash());
        existing.setCreatedAt(request.createdAt());
        existing.setUpdatedAt(request.updatedAt());
        return userRepository.save(existing);
    }

    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }
}
