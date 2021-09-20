package com.github.danieln96.currency.conversion.api.dao;

import java.io.Serializable;
import java.util.List;

import com.github.danieln96.currency.conversion.api.dto.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyData implements Serializable {
    private String table;
    private String currency;
    private CurrencyCode currencyCode;
    private List<Rate> rates;
}
