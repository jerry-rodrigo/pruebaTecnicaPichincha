package com.example.cambiotipocambio.controller;

import com.example.cambiotipocambio.dto.ExchangeRequestDTO;
import com.example.cambiotipocambio.dto.ExchangeResponseDTO;
import com.example.cambiotipocambio.service.ExperienciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador para gestionar las operaciones relacionadas con la experiencia del usuario.
 * Proporciona un endpoint para realizar el tipo de cambio.
 */
@RestController
@RequestMapping("/api/experiencia")
public class ExperienciaController {

    private final ExperienciaService experienciaService;

    public ExperienciaController(ExperienciaService experienciaService) {
        this.experienciaService = experienciaService;
    }

    /**
     * Endpoint para realizar el cálculo del tipo de cambio.
     *
     * @param request Datos necesarios para realizar el tipo de cambio (monto, monedas origen y destino).
     * @return Una respuesta con el resultado del tipo de cambio.
     */
    @PostMapping("/tipocambio")
    public Mono<ExchangeResponseDTO> realizarTipoCambio(@RequestBody ExchangeRequestDTO request) {
        return experienciaService.processExchange(request);
    }
}