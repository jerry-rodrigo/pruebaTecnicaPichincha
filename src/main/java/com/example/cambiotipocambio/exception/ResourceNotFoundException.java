package com.example.cambiotipocambio.exception;

/**
 * Excepción personalizada para manejar recursos no encontrados.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructor para crear una excepción con un mensaje personalizado.
     *
     * @param message Mensaje de error descriptivo.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}