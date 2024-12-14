package com.example.cambiotipocambio.security;

import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Filtro de autenticación JWT para validar tokens en cada solicitud.
 * Implementa la interfaz ServerAuthenticationConverter para convertir solicitudes en objetos de autenticación.
 */
@Component
public class JwtAuthenticationFilter implements ServerAuthenticationConverter {

    private final JwtUtil jwtUtil;
    private final ReactiveUserDetailsServiceImpl userDetailsService;

    /**
     * Constructor para inyectar dependencias.
     *
     * @param jwtUtil Utilidad para manejar tokens JWT.
     * @param userDetailsService Servicio para manejar detalles del usuario.
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, ReactiveUserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Convierte la solicitud entrante en un objeto de autenticación si contiene un token JWT válido.
     *
     * @param exchange Intercambio web del servidor.
     * @return Un Mono con la autenticación o vacío si el token no es válido.
     */
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Claims claims;
            try {
                claims = jwtUtil.validateToken(token);
            } catch (Exception e) {
                return Mono.empty();
            }
            String username = claims.getSubject();
            String role = (String) claims.get("role");
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

            return userDetailsService.findByUsername(username)
                    .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, authorities));
        }
        return Mono.empty();
    }
}