package com.example.cambiotipocambio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para Swagger/OpenAPI.
 * Configura la documentación de la API y el esquema de seguridad para autenticación JWT.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura la documentación de la API con OpenAPI.
     * Establece el título, la versión y los requisitos de seguridad para la autenticación JWT.
     *
     * @return Objeto OpenAPI personalizado para la documentación de la API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Tipo de Cambio")
                        .version("1.0")
                        .description("Documentación para la API de gestión de tipos de cambio"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(In.HEADER)
                                        .name("Authorization")));
    }
}