package com.example.cambiotipocambio.controller;

import com.example.cambiotipocambio.dto.ExchangeRequestDTO;
import com.example.cambiotipocambio.dto.ExchangeResponseDTO;
import com.example.cambiotipocambio.service.ExperienciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExperienciaControllerTest {

    @Mock
    private ExperienciaService experienciaService;

    @InjectMocks
    private ExperienciaController experienciaController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(experienciaController).build();
    }

    @Test
    void realizarTipoCambio_ShouldReturnExchangeResponse() {
        ExchangeRequestDTO request = new ExchangeRequestDTO();
        request.setUserId(1L);
        request.setMonto(100.0);
        request.setMonedaOrigen("USD");
        request.setMonedaDestino("EUR");

        ExchangeResponseDTO response = new ExchangeResponseDTO();
        response.setUserName("Test User");
        response.setInitialAmount(100.0);
        response.setFinalAmount(85.0);
        response.setCurrencyFrom("USD");
        response.setCurrencyTo("EUR");
        response.setExchangeRate(0.85);

        when(experienciaService.processExchange(any(ExchangeRequestDTO.class))).thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/api/experiencia/tipocambio")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeResponseDTO.class)
                .value(resp -> {
                    assertEquals("Test User", resp.getUserName());
                    assertEquals(100.0, resp.getInitialAmount());
                    assertEquals(85.0, resp.getFinalAmount());
                });

        verify(experienciaService, times(1)).processExchange(any(ExchangeRequestDTO.class));
    }
}