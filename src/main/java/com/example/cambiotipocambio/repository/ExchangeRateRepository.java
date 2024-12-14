package com.example.cambiotipocambio.repository;

import com.example.cambiotipocambio.domain.ExchangeRate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Repositorio reactivo para gestionar los tipos de cambio.
 * Proporciona operaciones CRUD y búsqueda personalizada por monedas origen y destino.
 */
@Repository
public interface ExchangeRateRepository extends ReactiveCrudRepository<ExchangeRate, Long> {
    /**
     * Busca un tipo de cambio basado en las monedas de origen y destino.
     *
     * @param from Moneda de origen.
     * @param to Moneda de destino.
     * @return Un Mono con el tipo de cambio encontrado, o vacío si no existe.
     */
    Mono<ExchangeRate> findByCurrencyFromAndCurrencyTo(String from, String to);
}