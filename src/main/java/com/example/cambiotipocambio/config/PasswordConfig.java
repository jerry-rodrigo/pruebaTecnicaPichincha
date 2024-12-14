package com.example.cambiotipocambio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración para proporcionar un bean de PasswordEncoder.
 * Esta clase configura el uso de BCryptPasswordEncoder para el hash de contraseñas.
 */
@Configuration
public class PasswordConfig {

    /**
     * Crea un bean de PasswordEncoder utilizando BCryptPasswordEncoder.
     * BCrypt es un algoritmo robusto y seguro para codificar contraseñas.
     *
     * @return una instancia de BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}