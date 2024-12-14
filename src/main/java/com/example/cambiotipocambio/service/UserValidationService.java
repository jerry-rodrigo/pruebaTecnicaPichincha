package com.example.cambiotipocambio.service;

import com.example.cambiotipocambio.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio para la validación de usuarios.
 * Proporciona funcionalidades para validar y obtener información de usuarios mediante servicios externos.
 */
@Service
public class UserValidationService {

    private final GoRestClientService goRestClientService;

    /**
     * Constructor para inyectar el servicio cliente de GoRest.
     *
     * @param goRestClientService Servicio cliente para interactuar con la API de GoRest.
     */
    public UserValidationService(GoRestClientService goRestClientService) {
        this.goRestClientService = goRestClientService;
    }

    /**
     * Valida un usuario por su ID y obtiene su nombre.
     *
     * @param userId ID del usuario a validar.
     * @return Mono con el nombre del usuario si es válido, o un error si no se encuentra.
     */
    public Mono<String> validateUserAndGetName(Long userId) {
        return goRestClientService.getUserName(userId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Usuario con ID " + userId + " no encontrado en GoRest")));
    }
}
