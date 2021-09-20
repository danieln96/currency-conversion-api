package com.github.danieln96.currency.conversion.api.client;

import java.util.Collections;

import com.github.danieln96.currency.conversion.api.dao.CurrencyData;
import com.github.danieln96.currency.conversion.api.dto.CurrencyCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NBPCurrencyClient {

    private final RestTemplate restTemplate;
    @Value("${nbp.currency.table.c}")
    private String url;

    @Cacheable("currencies")
    public CurrencyData getRateForCurrencyCode(final CurrencyCode currencyCode) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        final HttpEntity<String> entity = new HttpEntity<>("body", headers);

        final ResponseEntity<CurrencyData> rateResponseEntity = restTemplate.exchange(url.replace("{currencyCode}", currencyCode.toString()),
                HttpMethod.GET,
                entity,
                CurrencyData.class);

        if (HttpStatus.OK.equals(rateResponseEntity.getStatusCode())) {
            return rateResponseEntity.getBody();
        } else {
            throw new RestClientException("error while getting currency");
        }
    }
}
