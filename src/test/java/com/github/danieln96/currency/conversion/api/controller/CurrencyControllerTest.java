package com.github.danieln96.currency.conversion.api.controller;

import java.math.BigDecimal;
import java.util.Collections;

import com.github.danieln96.currency.conversion.api.client.NBPCurrencyClient;
import com.github.danieln96.currency.conversion.api.dao.CurrencyData;
import com.github.danieln96.currency.conversion.api.dao.Rate;
import com.github.danieln96.currency.conversion.api.dto.CurrencyCode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NBPCurrencyClient nbpCurrencyClient;

    @Test
    void correctConversions() throws Exception {

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

        mvc.perform(post("/api/currency/USD/")
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"currencyCode\":\"EUR\",\n" +
                        "  \"amount\":\"12.93\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "\"currencyCode\": \"USD\",\n" +
                        "\"amount\": 20.19\n" +
                        "}"));
    }

    @Test
    void badArgument() throws Exception {
        mvc.perform(post("/api/currency/UDD/")
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"currencyCode\":\"EUR\",\n" +
                        "  \"amount\":\"12.93\"\n" +
                        "}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void theSameCurrency() throws Exception {
        mvc.perform(post("/api/currency/USD/")
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "  \"currencyCode\":\"USD\",\n" +
                        "  \"amount\":\"12.93\"\n" +
                        "}"))
                .andExpect(status().is4xxClientError());
    }
}
