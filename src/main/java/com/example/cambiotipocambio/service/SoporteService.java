package com.example.cambiotipocambio.service;

import com.example.cambiotipocambio.domain.ExchangeRate;
import com.example.cambiotipocambio.exception.ResourceNotFoundException;
import com.example.cambiotipocambio.repository.ExchangeRateRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Servicio para gestionar tipos de cambio y operaciones relacionadas.
 * Proporciona funcionalidades CRUD y consultas específicas para tipos de cambio.
 */
@Service
public class SoporteService {

    private final ExchangeRateRepository exchangeRateRepository;

    /**
     * Constructor para inyectar el repositorio de tipos de cambio.
     *
     * @param exchangeRateRepository Repositorio de tipos de cambio.
     */
    public SoporteService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    /**
     * Guarda un nuevo tipo de cambio en la base de datos.
     *
     * @param exchangeRate Objeto ExchangeRate a guardar.
     * @return Mono con el tipo de cambio guardado.
     */
    public Mono<ExchangeRate> save(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }

    /**
     * Actualiza un tipo de cambio existente por su ID.
     *
     * @param id ID del tipo de cambio a actualizar.
     * @param newRate Nuevos datos para el tipo de cambio.
     * @return Mono con el tipo de cambio actualizado o error si no se encuentra.
     */
    public Mono<ExchangeRate> update(Long id, ExchangeRate newRate) {
        return exchangeRateRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se encontró el tipo de cambio a actualizar")))
                .flatMap(oldRate -> {
                    oldRate.setRate(newRate.getRate());
                    oldRate.setCurrencyFrom(newRate.getCurrencyFrom());
                    oldRate.setCurrencyTo(newRate.getCurrencyTo());
                    return exchangeRateRepository.save(oldRate);
                });
    }

    /**
     * Busca un tipo de cambio por su ID.
     *
     * @param id ID del tipo de cambio.
     * @return Mono con el tipo de cambio encontrado o error si no existe.
     */
    public Mono<ExchangeRate> findById(Long id) {
        return exchangeRateRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Tipo de cambio no encontrado")));
    }

    /**
     * Lista todos los tipos de cambio almacenados en la base de datos.
     *
     * @return Flux con todos los tipos de cambio.
     */
    public Flux<ExchangeRate> findAll() {
        return exchangeRateRepository.findAll();
    }

    /**
     * Busca un tipo de cambio basado en las monedas de origen y destino.
     *
     * @param from Moneda de origen.
     * @param to Moneda de destino.
     * @return Mono con el tipo de cambio encontrado o vacío si no existe.
     */
    public Mono<ExchangeRate> findRate(String from, String to) {
        return exchangeRateRepository.findByCurrencyFromAndCurrencyTo(from, to);
    }
}