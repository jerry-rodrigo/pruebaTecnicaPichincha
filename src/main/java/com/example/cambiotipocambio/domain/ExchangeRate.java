package com.example.cambiotipocambio.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("exchange_rate")
public class ExchangeRate {
    @Id
    private Long id;
    private String currencyFrom;
    private String currencyTo;
    private Double rate;
}