package com.github.danieln96.currency.conversion.api.service;

import java.math.BigDecimal;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommissionServiceTest {

    private static final CommissionService COMMISSION_SERVICE = new CommissionService();

    @Test
    void correctNumber() {
        Assertions.assertEquals(COMMISSION_SERVICE.accrualCommission(BigDecimal.ONE), BigDecimal.valueOf(1.02));
    }

    @Test
    void amountIsNull() {
        Assertions.assertEquals(COMMISSION_SERVICE.accrualCommission(null), null);
    }
}
