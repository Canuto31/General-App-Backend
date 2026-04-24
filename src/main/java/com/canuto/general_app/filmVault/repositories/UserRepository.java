package com.canuto.general_app.filmVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.canuto.general_app.filmVault.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    default User create(User user) {
        return save(user);
    }
}
