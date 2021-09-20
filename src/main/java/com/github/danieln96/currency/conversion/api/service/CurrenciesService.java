package com.github.danieln96.currency.conversion.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.github.danieln96.currency.conversion.api.client.NBPCurrencyClient;
import com.github.danieln96.currency.conversion.api.dao.CurrencyData;
import com.github.danieln96.currency.conversion.api.dao.Rate;
import com.github.danieln96.currency.conversion.api.dto.CurrencyAmount;
import com.github.danieln96.currency.conversion.api.dto.CurrencyCode;
import com.github.danieln96.currency.conversion.api.exception.NBPClientMissingDataException;
import com.github.danieln96.currency.conversion.api.exception.SameCurrencyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrenciesService {

    private final NBPCurrencyClient client;
    private final CommissionService commissionService;

    public CurrencyAmount convertToOtherCurrency(final CurrencyAmount currencyAmount, final CurrencyCode destinationCurrencyCode) {

        if (currencyAmount.getCurrencyCode() == destinationCurrencyCode) {
            throw new SameCurrencyException(String.format("Trying to convert to the same currency [%s]", destinationCurrencyCode));
        }

        if (currencyAmount.getAmount().signum() != 1) {
            throw new IllegalArgumentException("amount value is not positive");
        }

        if (currencyAmount.getCurrencyCode() != CurrencyCode.PLN) {
            final CurrencyData currencyData = client.getRateForCurrencyCode(currencyAmount.getCurrencyCode());

            final BigDecimal amountInPLN = commissionService.accrualCommission(
                    currencyAmount.getAmount().multiply(
                            getRate(currencyData).getSalePrice()
                    )
            );

            if (CurrencyCode.PLN == destinationCurrencyCode) {
                return new CurrencyAmount(CurrencyCode.PLN, amountInPLN.setScale(2, RoundingMode.CEILING));
            }

            return buyCurrencyForPLN(destinationCurrencyCode, amountInPLN);

        } else {

            return buyCurrencyForPLN(destinationCurrencyCode, currencyAmount.getAmount());

        }
    }

    private CurrencyAmount buyCurrencyForPLN(final CurrencyCode destinationCurrencyCode, final BigDecimal amount) {
        final CurrencyData destinationCurrencyData = client.getRateForCurrencyCode(destinationCurrencyCode);

        return new CurrencyAmount(destinationCurrencyCode, commissionService.accrualCommission(
                amount.divide(
                        getRate(destinationCurrencyData).getPurscharePrice(), RoundingMode.HALF_UP)));

    }

    private Rate getRate(final CurrencyData currencyData) {
        if (currencyData != null && currencyData.getRates() != null && currencyData.getRates().size() == 1) {
            return currencyData.getRates().get(0);
        }

        throw new NBPClientMissingDataException("error while getting Currency Rate");
    }
}
