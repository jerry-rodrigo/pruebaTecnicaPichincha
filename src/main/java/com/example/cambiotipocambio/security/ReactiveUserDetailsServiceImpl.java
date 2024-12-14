package com.example.cambiotipocambio.security;

import com.example.cambiotipocambio.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Servicio reactivo para gestionar los detalles del usuario.
 */
@Service
public class ReactiveUserDetailsServiceImpl {

    private final UserRepository userRepository;

    /**
     * Constructor para inyectar el repositorio de usuarios.
     *
     * @param userRepository Repositorio para acceder a los datos del usuario.
     */
    public ReactiveUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Busca un usuario por su nombre de usuario y devuelve los detalles del usuario.
     *
     * @param username Nombre de usuario.
     * @return Mono con los detalles del usuario o vacío si no existe.
     */
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(appUser -> User.withUsername(appUser.getUsername())
                        .password(appUser.getPassword())
                        .authorities(appUser.getRole())
                        .build());
    }
}