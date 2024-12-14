package com.example.cambiotipocambio.controller;

import com.example.cambiotipocambio.domain.AppUser;
import com.example.cambiotipocambio.dto.CreateUserRequest;
import com.example.cambiotipocambio.security.JwtUtil;
import com.example.cambiotipocambio.security.ReactiveUserDetailsServiceImpl;
import com.example.cambiotipocambio.security.Role;
import com.example.cambiotipocambio.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador de autenticación para la API.
 * Proporciona endpoints para el inicio de sesión (login) y registro de usuarios (signup).
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ReactiveUserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserManagementService userManagementService;

    /**
     * Constructor para inyectar las dependencias necesarias.
     *
     * @param userDetailsService Servicio para manejar detalles de usuario.
     * @param jwtUtil Utilidad para generación y validación de tokens JWT.
     * @param passwordEncoder Codificador de contraseñas para almacenamiento seguro.
     * @param userManagementService Servicio para la gestión de usuarios.
     */
    @Autowired
    public AuthController(ReactiveUserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder, UserManagementService userManagementService) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userManagementService = userManagementService;
    }

    /**
     * Endpoint para el inicio de sesión.
     * Valida las credenciales del usuario y genera un token JWT si son correctas.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña del usuario.
     * @return Token JWT si las credenciales son válidas.
     */
    @PostMapping("/login")
    public Mono<String> login(@RequestParam String username, @RequestParam String password) {
        return userDetailsService.findByUsername(username)
                .flatMap(userDetails -> {
                    if (passwordEncoder.matches(password, userDetails.getPassword())) {
                        String role = userDetails.getAuthorities().iterator().next().getAuthority();
                        String token = jwtUtil.generateToken(username, Role.valueOf(role));
                        return Mono.just(token);
                    }
                    return Mono.error(new RuntimeException("Credenciales inválidas"));
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Usuario no encontrado")));
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     * Almacena el usuario en la base de datos con la contraseña codificada.
     *
     * @param request Datos del usuario para el registro.
     * @return El usuario registrado.
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AppUser> signup(@RequestBody CreateUserRequest request) {
        AppUser user = AppUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .name(request.getName())
                .build();
        return userManagementService.createUser(user);
    }
}