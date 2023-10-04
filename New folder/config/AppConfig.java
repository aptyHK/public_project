package com.javahk.project.finnhub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Value(value = "${api.finnhub.token}")
    private String token;

    @Bean
    String finnhubToken() {
        return token;
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
