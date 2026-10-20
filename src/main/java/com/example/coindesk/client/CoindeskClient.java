package com.example.coindesk.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.coindesk.dto.CoindeskResponse;

@Component
public class CoindeskClient {

    private final RestTemplate restTemplate;

    private final String url;

    public CoindeskClient(RestTemplateBuilder builder, @Value("${coindesk.url}") String url) {
        this.restTemplate = builder.build();
        this.url = url;
    }

    public CoindeskResponse fetch() {
        return restTemplate.getForObject(url, CoindeskResponse.class);
    }
}
