package com.canuto.general_app.filmVault.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.canuto.general_app.filmVault.dto.UserCreateRequest;
import com.canuto.general_app.filmVault.models.User;
import com.canuto.general_app.filmVault.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void create_shouldPersistUser() {
        when(userRepository.create(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(2L); // simulate @GeneratedValue
            return user;
        });

        LocalDateTime now = LocalDateTime.of(2026, 4, 16, 12, 0);
        UserCreateRequest request = new UserCreateRequest(
                "john",
                "john@example.com",
                "hash",
                now,
                null);

        User created = userService.create(request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).create(captor.capture());

        User saved = captor.getValue();
        assertEquals(2L, saved.getId());
        assertEquals("john", saved.getUserName());
        assertEquals("john@example.com", saved.getEmail());
        assertEquals("hash", saved.getPasswordHash());
        assertEquals(created, saved);
    }
}

