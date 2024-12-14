package com.example.cambiotipocambio.service;

import com.example.cambiotipocambio.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserValidationServiceTest {

    @Mock
    private GoRestClientService goRestClientService;

    @InjectMocks
    private UserValidationService userValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateUserAndGetName_ShouldReturnUserName_WhenUserExists() {
        Long userId = 1L;
        String expectedName = "Test User";
        when(goRestClientService.getUserName(userId)).thenReturn(Mono.just(expectedName));

        Mono<String> result = userValidationService.validateUserAndGetName(userId);

        assertNotNull(result);
        result.subscribe(userName -> assertEquals(expectedName, userName));
        verify(goRestClientService, times(1)).getUserName(userId);
    }

    @Test
    void validateUserAndGetName_ShouldThrowException_WhenUserDoesNotExist() {
        Long userId = 1L;
        when(goRestClientService.getUserName(userId)).thenReturn(Mono.empty());

        Mono<String> result = userValidationService.validateUserAndGetName(userId);

        assertThrows(ResourceNotFoundException.class, () -> result.block());
        verify(goRestClientService, times(1)).getUserName(userId);
    }
}