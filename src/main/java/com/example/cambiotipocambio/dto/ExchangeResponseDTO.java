package com.example.cambiotipocambio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExchangeResponseDTO {
    private String userName;
    private Double initialAmount;
    private Double finalAmount;
    private String currencyFrom;
    private String currencyTo;
    private Double exchangeRate;
    private LocalDateTime processDate;
    private String errorMessage;
}