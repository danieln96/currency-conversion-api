package com.github.danieln96.currency.conversion.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

@Service
public class CommissionService {

    private static final BigDecimal COMMISSION_VALUE = BigDecimal.valueOf(0.02);

    public BigDecimal accrualCommission(final BigDecimal amount) {
        return amount != null ? amount.multiply(COMMISSION_VALUE.add(BigDecimal.ONE)).setScale(2, RoundingMode.CEILING) : null;
    }
}
