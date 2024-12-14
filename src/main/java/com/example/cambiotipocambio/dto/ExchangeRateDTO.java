package com.example.cambiotipocambio.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ExchangeRateDTO {
    private Long id;

    @NotBlank(message = "currencyFrom no puede ser vacío")
    private String currencyFrom;

    @NotBlank(message = "currencyTo no puede ser vacío")
    private String currencyTo;

    @NotNull(message = "rate no puede ser nulo")
    @Positive(message = "rate debe ser mayor a 0")
    private Double rate;
}