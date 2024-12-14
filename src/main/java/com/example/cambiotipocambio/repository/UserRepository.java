package com.example.cambiotipocambio.repository;

import com.example.cambiotipocambio.domain.AppUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Repositorio reactivo para gestionar los usuarios de la aplicación.
 * Proporciona operaciones CRUD y búsqueda personalizada por nombre de usuario.
 */
@Repository
public interface UserRepository extends ReactiveCrudRepository<AppUser, Long> {
    /**
     * Busca un usuario basado en su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return Un Mono con el usuario encontrado, o vacío si no existe.
     */
    Mono<AppUser> findByUsername(String username);
}