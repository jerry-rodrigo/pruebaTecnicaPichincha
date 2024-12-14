package com.example.cambiotipocambio.repository;

import com.example.cambiotipocambio.domain.ExchangeOperation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio reactivo para gestionar las operaciones de tipo de cambio.
 * Proporciona operaciones CRUD sobre la entidad ExchangeOperation.
 */
@Repository
public interface ExchangeOperationRepository extends ReactiveCrudRepository<ExchangeOperation, Long> {
}