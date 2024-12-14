package com.example.cambiotipocambio.service;

import com.example.cambiotipocambio.domain.AppUser;
import com.example.cambiotipocambio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserManagementServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserManagementService userManagementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        AppUser user = AppUser.builder().username("testuser").build();
        when(userRepository.save(any(AppUser.class))).thenReturn(Mono.just(user));

        Mono<AppUser> result = userManagementService.createUser(user);

        assertNotNull(result);
        result.subscribe(createdUser -> assertEquals("testuser", createdUser.getUsername()));
        verify(userRepository, times(1)).save(user);
    }
}