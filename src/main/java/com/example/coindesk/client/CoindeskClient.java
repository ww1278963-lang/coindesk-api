package com.example.coindesk.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.coindesk.dto.CoindeskResponse;
import com.example.coindesk.exception.CoindeskUnavailableException;

@Component
public class CoindeskClient {

    private final RestTemplate restTemplate;

    private final String url;

    public CoindeskClient(RestTemplateBuilder builder, @Value("${coindesk.url}") String url) {
        this.restTemplate = builder.build();
        this.url = url;
    }

    public CoindeskResponse fetch() {
        try {
            CoindeskResponse response = restTemplate.getForObject(url, CoindeskResponse.class);
            if (response == null) {
                throw new CoindeskUnavailableException("coindesk API 沒有回傳內容");
            }
            return response;
        } catch (RestClientException e) {
            //連不上或回傳有問題時
            throw new CoindeskUnavailableException("coindesk API 暫時無法使用");
        }
    }
}
