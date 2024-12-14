package com.example.cambiotipocambio.service;

import com.example.cambiotipocambio.domain.AppUser;
import com.example.cambiotipocambio.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio para la gestión de usuarios de la aplicación.
 * Proporciona funcionalidades para crear usuarios y gestionarlos en el sistema.
 */
@Service
public class UserManagementService {

    private final UserRepository userRepository;

    /**
     * Constructor para inyectar el repositorio de usuarios.
     *
     * @param userRepository Repositorio para la gestión de datos de usuarios.
     */
    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param user Objeto AppUser con los datos del usuario a crear.
     * @return Mono con el usuario creado.
     */
    public Mono<AppUser> createUser(AppUser user) {
        return userRepository.save(user);
    }
}