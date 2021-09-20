package com.github.danieln96.currency.conversion.api.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.danieln96.currency.conversion.api.dto.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rate  implements Serializable {

    @JsonProperty("no")
    private String rateNumber;
    @JsonProperty("bid")
    private BigDecimal purscharePrice;
    @JsonProperty("ask")
    private BigDecimal salePrice;
    private CurrencyCode currencyCode;
}
