package com.example.cambiotipocambio.config;

import com.example.cambiotipocambio.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Configura las políticas de acceso, filtros y excepciones mediante Spring Security WebFlux.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * Configura el filtro de seguridad para la aplicación.
     * Define las políticas de acceso basadas en roles y desactiva CSRF para endpoints específicos.
     *
     * @param http        Configuración de seguridad HTTP.
     * @param jwtFilter   Filtro personalizado para la autenticación basada en JWT.
     * @return Configuración de la cadena de filtros de seguridad.
     */
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, JwtAuthenticationFilter jwtFilter) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/swagger-ui.html", "/webjars/**", "/v3/api-docs/**", "/swagger-ui/**", "/h2-console/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER")
                        .anyExchange().authenticated()
                )
                .headers(headers -> headers.frameOptions().disable()) // Permite el uso de frames para la consola H2.
                .addFilterAt((exchange, chain) -> jwtFilter.convert(exchange)
                        .flatMap(authentication -> {
                            return chain.filter(exchange)
                                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(new SecurityContextImpl(authentication))));
                        })
                        .switchIfEmpty(chain.filter(exchange)), SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() ->
                                swe.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED)))
                        .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() ->
                                swe.getResponse().setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN)))
                )
                .build();
    }
}