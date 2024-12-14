package com.example.cambiotipocambio.service;

import com.example.cambiotipocambio.client.GoRestUserResponse;
import com.example.cambiotipocambio.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Servicio cliente para interactuar con la API de GoRest.
 * Proporciona funcionalidades para obtener información de usuarios desde la API remota.
 */
@Service
public class GoRestClientService {

    private final WebClient webClientGoRest;

    /**
     * Constructor para inyectar la instancia de WebClient configurada.
     *
     * @param webClientGoRest Cliente web configurado para la API de GoRest.
     */
    public GoRestClientService(WebClient webClientGoRest) {
        this.webClientGoRest = webClientGoRest;
    }

    /**
     * Obtiene el nombre de un usuario desde la API de GoRest usando su ID.
     *
     * @param userId ID del usuario a consultar.
     * @return Un Mono con el nombre del usuario si se encuentra, o una excepción si no.
     */
    public Mono<String> getUserName(Long userId) {
        return webClientGoRest.get()
                .uri("/users/{id}", userId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ResourceNotFoundException("Usuario con ID " + userId + " no encontrado en GoRest"));
                    }
                    return Mono.error(new RuntimeException("Error al consultar el usuario en GoRest"));
                })
                .bodyToMono(GoRestUserResponse.class)
                .map(GoRestUserResponse::getName);
    }
}