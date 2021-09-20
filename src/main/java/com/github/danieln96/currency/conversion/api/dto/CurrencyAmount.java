package com.github.danieln96.currency.conversion.api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyAmount {

    private CurrencyCode currencyCode;
    private BigDecimal amount;
}
