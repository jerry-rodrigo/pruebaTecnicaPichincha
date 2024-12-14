package com.example.cambiotipocambio.controller;

import com.example.cambiotipocambio.domain.ExchangeRate;
import com.example.cambiotipocambio.service.SoporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SoporteControllerTest {

    @Mock
    private SoporteService soporteService;

    @InjectMocks
    private SoporteController soporteController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(soporteController).build();
    }

    @Test
    void listar_ShouldReturnListOfExchangeRates() {
        ExchangeRate rate = ExchangeRate.builder()
                .currencyFrom("USD")
                .currencyTo("EUR")
                .rate(0.85)
                .build();

        when(soporteService.findAll()).thenReturn(Flux.just(rate));

        webTestClient.get()
                .uri("/api/soporte/tipocambio")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ExchangeRate.class)
                .value(rates -> assertEquals(1, rates.size()));

        verify(soporteService, times(1)).findAll();
    }
}