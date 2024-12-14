package com.example.cambiotipocambio.controller;

import com.example.cambiotipocambio.domain.ExchangeRate;
import com.example.cambiotipocambio.dto.ExchangeRateDTO;
import com.example.cambiotipocambio.service.SoporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Controlador para gestionar las operaciones de soporte relacionadas con los tipos de cambio.
 * Proporciona endpoints para listar, registrar, actualizar y obtener tipos de cambio.
 */
@RestController
@RequestMapping("/api/soporte")
public class SoporteController {

    private final SoporteService soporteService;

    public SoporteController(SoporteService soporteService) {
        this.soporteService = soporteService;
    }

    /**
     * Endpoint para listar todos los tipos de cambio registrados.
     *
     * @return Una lista de tipos de cambio disponibles.
     */
    @GetMapping("/tipocambio")
    public Flux<ExchangeRate> listar() {
        return soporteService.findAll();
    }

    /**
     * Endpoint para obtener un tipo de cambio específico por su ID.
     *
     * @param id Identificador del tipo de cambio.
     * @return El tipo de cambio correspondiente al ID proporcionado.
     */
    @GetMapping("/tipocambio/{id}")
    public Mono<ExchangeRate> obtener(@PathVariable Long id) {
        return soporteService.findById(id);
    }

    /**
     * Endpoint para registrar un nuevo tipo de cambio.
     *
     * @param dto Datos necesarios para crear un nuevo tipo de cambio.
     * @return El tipo de cambio registrado.
     */
    @PostMapping("/tipocambio")
    public Mono<ExchangeRate> registrar(@Valid @RequestBody ExchangeRateDTO dto) {
        ExchangeRate rate = ExchangeRate.builder()
                .currencyFrom(dto.getCurrencyFrom())
                .currencyTo(dto.getCurrencyTo())
                .rate(dto.getRate())
                .build();
        return soporteService.save(rate);
    }

    /**
     * Endpoint para actualizar un tipo de cambio existente.
     *
     * @param id Identificador del tipo de cambio a actualizar.
     * @param dto Datos actualizados del tipo de cambio.
     * @return El tipo de cambio actualizado.
     */
    @PutMapping("/tipocambio/{id}")
    public Mono<ExchangeRate> actualizar(@PathVariable Long id, @RequestBody ExchangeRateDTO dto) {
        ExchangeRate rate = ExchangeRate.builder()
                .currencyFrom(dto.getCurrencyFrom())
                .currencyTo(dto.getCurrencyTo())
                .rate(dto.getRate())
                .build();
        return soporteService.update(id, rate);
    }
}