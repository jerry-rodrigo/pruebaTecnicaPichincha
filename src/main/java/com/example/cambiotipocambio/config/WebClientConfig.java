package com.example.cambiotipocambio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Clase de configuración para WebClient.
 * Proporciona un cliente web configurado para consumir servicios REST, como el de GoRest.
 */
@Configuration
public class WebClientConfig {

    /**
     * Configura un cliente web para interactuar con la API de GoRest.
     *
     * @return una instancia de WebClient configurada con la URL base de GoRest.
     */
    @Bean
    public WebClient webClientGoRest() {
        return WebClient.builder()
                .baseUrl("https://gorest.co.in/public/v2")
                .build();
    }
}