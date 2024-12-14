package com.example.cambiotipocambio.dto;

import lombok.Data;

@Data
public class ExchangeRequestDTO {
    private Long userId;
    private Double monto;
    private String monedaOrigen;
    private String monedaDestino;
}