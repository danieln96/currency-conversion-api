package com.github.danieln96.currency.conversion.api.controller;

import com.github.danieln96.currency.conversion.api.dto.CurrencyCode;
import com.github.danieln96.currency.conversion.api.dto.CurrencyAmount;
import com.github.danieln96.currency.conversion.api.service.CurrenciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrenciesService currenciesService;

    @PostMapping("/currency/{currencyCode}/")
    public CurrencyAmount changeCurrency(@RequestBody final CurrencyAmount currencyAmount,
                                         @PathVariable("currencyCode") final CurrencyCode destinationCurrencyCode) {

        return currenciesService.convertToOtherCurrency(currencyAmount, destinationCurrencyCode);
    }

}
