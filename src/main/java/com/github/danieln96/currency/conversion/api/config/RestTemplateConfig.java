package com.github.danieln96.currency.conversion.api.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(500))
                .setReadTimeout(Duration.ofMillis(500))
                .build();
    }
}
