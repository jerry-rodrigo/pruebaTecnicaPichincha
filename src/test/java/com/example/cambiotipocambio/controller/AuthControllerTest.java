package com.example.cambiotipocambio.controller;

import com.example.cambiotipocambio.domain.AppUser;
import com.example.cambiotipocambio.dto.CreateUserRequest;
import com.example.cambiotipocambio.security.JwtUtil;
import com.example.cambiotipocambio.service.UserManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserManagementService userManagementService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(authController).build();
    }

    @Test
    void signup_ShouldReturnCreatedUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("testuser");
        request.setPassword("password");
        request.setRole("ROLE_USER");
        request.setName("Test User");

        AppUser user = AppUser.builder()
                .username("testuser")
                .name("Test User")
                .role("ROLE_USER")
                .build();

        when(userManagementService.createUser(any(AppUser.class))).thenReturn(Mono.just(user));

        webTestClient.post()
                .uri("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AppUser.class)
                .value(response -> {
                    assertEquals("testuser", response.getUsername());
                    assertEquals("Test User", response.getName());
                    assertEquals("ROLE_USER", response.getRole());
                });

        verify(userManagementService, times(1)).createUser(any(AppUser.class));
    }
}