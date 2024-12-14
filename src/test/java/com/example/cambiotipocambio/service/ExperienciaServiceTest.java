package com.example.cambiotipocambio.service;

import com.example.cambiotipocambio.domain.ExchangeOperation;
import com.example.cambiotipocambio.domain.ExchangeRate;
import com.example.cambiotipocambio.dto.ExchangeRequestDTO;
import com.example.cambiotipocambio.dto.ExchangeResponseDTO;
import com.example.cambiotipocambio.exception.ResourceNotFoundException;
import com.example.cambiotipocambio.repository.ExchangeOperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ExperienciaServiceTest {

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private SoporteService soporteService;

    @Mock
    private ExchangeOperationRepository exchangeOperationRepository;

    @InjectMocks
    private ExperienciaService experienciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processExchange_ShouldReturnExchangeResponse_WhenValidRequest() {
        ExchangeRequestDTO request = new ExchangeRequestDTO();
        request.setUserId(1L);
        request.setMonto(100.0);
        request.setMonedaOrigen("USD");
        request.setMonedaDestino("EUR");

        String userName = "Test User";
        double rateValue = 0.85;
        double finalAmount = 100.0 * rateValue;

        // Mock del tipo de cambio
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .currencyFrom("USD")
                .currencyTo("EUR")
                .rate(rateValue)
                .build();

        // Mock de la operación de cambio
        ExchangeOperation operation = ExchangeOperation.builder()
                .userName(userName)
                .initialAmount(100.0)
                .finalAmount(finalAmount)
                .exchangeRate(rateValue)
                .currencyFrom("USD")
                .currencyTo("EUR")
                .processDate(LocalDateTime.now())
                .build();

        when(userValidationService.validateUserAndGetName(eq(1L))).thenReturn(Mono.just(userName));
        when(soporteService.findRate(eq("USD"), eq("EUR"))).thenReturn(Mono.just(exchangeRate));
        when(exchangeOperationRepository.save(any(ExchangeOperation.class))).thenReturn(Mono.just(operation));

        Mono<ExchangeResponseDTO> result = experienciaService.processExchange(request);

        assertNotNull(result);
        result.subscribe(response -> {
            assertEquals(userName, response.getUserName());
            assertEquals(100.0, response.getInitialAmount());
            assertEquals(finalAmount, response.getFinalAmount());
            assertEquals("USD", response.getCurrencyFrom());
            assertEquals("EUR", response.getCurrencyTo());
            assertEquals(rateValue, response.getExchangeRate());
        });

        verify(userValidationService, times(1)).validateUserAndGetName(eq(1L));
        verify(soporteService, times(1)).findRate(eq("USD"), eq("EUR"));
        verify(exchangeOperationRepository, times(1)).save(any(ExchangeOperation.class));
    }

    @Test
    void processExchange_ShouldThrowException_WhenRateNotFound() {
        ExchangeRequestDTO request = new ExchangeRequestDTO();
        request.setUserId(1L);
        request.setMonto(100.0);
        request.setMonedaOrigen("USD");
        request.setMonedaDestino("EUR");

        String userName = "Test User";

        when(userValidationService.validateUserAndGetName(eq(1L))).thenReturn(Mono.just(userName));
        when(soporteService.findRate(eq("USD"), eq("EUR"))).thenReturn(Mono.empty());

        Mono<ExchangeResponseDTO> result = experienciaService.processExchange(request);

        assertThrows(ResourceNotFoundException.class, () -> result.block());

        verify(userValidationService, times(1)).validateUserAndGetName(eq(1L));
        verify(soporteService, times(1)).findRate(eq("USD"), eq("EUR"));
        verify(exchangeOperationRepository, never()).save(any(ExchangeOperation.class));
    }
}