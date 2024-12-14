package com.example.cambiotipocambio.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("exchange_operation")
public class ExchangeOperation {
    @Id
    private Long id;
    private String userName;
    private Double initialAmount;
    private Double finalAmount;
    private Double exchangeRate;
    private String currencyFrom;
    private String currencyTo;
    private LocalDateTime processDate;
}