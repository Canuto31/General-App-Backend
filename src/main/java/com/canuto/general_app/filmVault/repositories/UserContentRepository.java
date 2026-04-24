package com.canuto.general_app.filmVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canuto.general_app.filmVault.models.UserContent;

public interface UserContentRepository extends JpaRepository<UserContent, Long> {
    
    default UserContent create(UserContent userContent) {
        return save(userContent);
    }
}
