package com.github.danieln96.currency.conversion.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;

import com.github.danieln96.currency.conversion.api.client.NBPCurrencyClient;
import com.github.danieln96.currency.conversion.api.dao.CurrencyData;
import com.github.danieln96.currency.conversion.api.dao.Rate;
import com.github.danieln96.currency.conversion.api.dto.CurrencyAmount;
import com.github.danieln96.currency.conversion.api.dto.CurrencyCode;
import com.github.danieln96.currency.conversion.api.exception.NBPClientMissingDataException;
import com.github.danieln96.currency.conversion.api.exception.SameCurrencyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CurrenciesServiceTest {

    @Autowired
    private CommissionService commissionService;
    @MockBean
    private NBPCurrencyClient nbpCurrencyClient;
    @Autowired
    private CurrenciesService currenciesService;

    @Test
    void correctValues() {
        Mockito.when(nbpCurrencyClient.getRateForCurrencyCode(CurrencyCode.EUR))
                .thenReturn(new CurrencyData("table_name",
                        "currency_name",
                        CurrencyCode.EUR,
                        Collections.singletonList(new Rate("rate_number",
                                BigDecimal.valueOf(0.6),
                                BigDecimal.valueOf(1.5),
                                CurrencyCode.EUR))));

        Mockito.when(nbpCurrencyClient.getRateForCurrencyCode(CurrencyCode.USD))
                .thenReturn(new CurrencyData("table_name",
                        "currency_name",
                        CurrencyCode.USD,
                        Collections.singletonList(new Rate("rate_number",
                                BigDecimal.valueOf(1),
                                BigDecimal.valueOf(1.1),
                                CurrencyCode.USD))));

        Assertions.assertEquals(new CurrencyAmount(CurrencyCode.USD, new BigDecimal(15.61).setScale(2, RoundingMode.CEILING)),
                currenciesService.convertToOtherCurrency(new CurrencyAmount(CurrencyCode.EUR, BigDecimal.TEN), CurrencyCode.USD));
    }

    @Test
    void theSameCurrency() {
        Assertions.assertThrows(SameCurrencyException.class, () -> currenciesService.convertToOtherCurrency(new CurrencyAmount(CurrencyCode.USD, BigDecimal.TEN), CurrencyCode.USD));
    }

    @Test
    void convertFromPLN() {

        Mockito.when(nbpCurrencyClient.getRateForCurrencyCode(CurrencyCode.EUR))
                .thenReturn(new CurrencyData("table_name",
                        "currency_name",
                        CurrencyCode.EUR,
                        Collections.singletonList(new Rate("rate_number",
                                BigDecimal.valueOf(0.6),
                                BigDecimal.valueOf(1.5),
                                CurrencyCode.EUR))));

        Assertions.assertEquals(new CurrencyAmount(CurrencyCode.EUR, new BigDecimal(17.34).setScale(2, RoundingMode.CEILING)),
                currenciesService.convertToOtherCurrency(new CurrencyAmount(CurrencyCode.PLN, BigDecimal.TEN), CurrencyCode.EUR));

    }

    @Test
    void convertToPLN() {
        Mockito.when(nbpCurrencyClient.getRateForCurrencyCode(CurrencyCode.EUR))
                .thenReturn(new CurrencyData("table_name",
                        "currency_name",
                        CurrencyCode.EUR,
                        Collections.singletonList(new Rate("rate_number",
                                BigDecimal.valueOf(0.6),
                                BigDecimal.valueOf(1.5),
                                CurrencyCode.EUR))));

        Assertions.assertEquals(new CurrencyAmount(CurrencyCode.PLN, new BigDecimal(15.299).setScale(2, RoundingMode.CEILING)),
                currenciesService.convertToOtherCurrency(new CurrencyAmount(CurrencyCode.EUR, BigDecimal.TEN), CurrencyCode.PLN));


    }

    @Test
    void badCurrencyDataStructure() {
        Mockito.when(nbpCurrencyClient.getRateForCurrencyCode(CurrencyCode.EUR))
                .thenReturn(new CurrencyData("table_name",
                        "currency_name",
                        CurrencyCode.EUR,
                        null));

        Assertions.assertThrows(NBPClientMissingDataException.class,
                () -> currenciesService.convertToOtherCurrency(new CurrencyAmount(CurrencyCode.EUR, BigDecimal.TEN), CurrencyCode.PLN));
    }
}
