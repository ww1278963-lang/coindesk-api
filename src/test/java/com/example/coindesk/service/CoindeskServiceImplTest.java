package com.example.coindesk.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.coindesk.client.CoindeskClient;
import com.example.coindesk.dto.CoindeskBpi;
import com.example.coindesk.dto.CoindeskResponse;
import com.example.coindesk.dto.RateResponse;
import com.example.coindesk.entity.Currency;
import com.example.coindesk.repository.CurrencyRepository;
import com.example.coindesk.service.impl.CoindeskServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

//轉換邏輯的單元測試 不連網路也不碰資料庫
@ExtendWith(MockitoExtension.class)
class CoindeskServiceImplTest {

    @Mock
    private CoindeskClient coindeskClient;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CoindeskServiceImpl coindeskService;

    private CoindeskResponse source;

    @BeforeEach
    void setUp() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/coindesk-sample.json")) {
            source = new ObjectMapper().readValue(in, CoindeskResponse.class);
        }
        when(coindeskClient.fetch()).thenReturn(source);
    }

    @Test
    void timeToTaipei() {
        RateResponse result = coindeskService.getTransformed();

        //UTC 07:07:20 → 台灣 15:07:20
        assertThat(result.getTime()).isEqualTo(LocalDateTime.of(2024, 9, 2, 15, 7, 20));
    }

    @Test
    void sortedByCode() {
        RateResponse result = coindeskService.getTransformed();

        assertThat(result.getBpi().keySet()).containsExactly("EUR", "GBP", "USD");
        assertThat(result.getBpi().get("USD").getRate()).isEqualByComparingTo(new BigDecimal("57756.2984"));
    }

    @Test
    void nameZhFromTableFirst() {
        when(currencyRepository.findAll()).thenReturn(Arrays.asList(
                new Currency("USD", "美金"),
                new Currency("GBP", "英鎊")));

        RateResponse result = coindeskService.getTransformed();

        //表裡有就用表的
        assertThat(result.getBpi().get("USD").getNameZh()).isEqualTo("美金");
        assertThat(result.getBpi().get("GBP").getNameZh()).isEqualTo("英鎊");
        //表裡沒有就用JDK的
        assertThat(result.getBpi().get("EUR").getNameZh()).isEqualTo("歐元");
    }

    @Test
    void unknownCodeNameZhIsNull() {
        CoindeskBpi unknown = new CoindeskBpi();
        unknown.setCode("ZZZ");
        unknown.setRateFloat(new BigDecimal("1.5"));
        source.getBpi().put("ZZZ", unknown);

        RateResponse result = coindeskService.getTransformed();

        assertThat(result.getBpi().get("ZZZ").getNameZh()).isNull();
        assertThat(result.getBpi().get("ZZZ").getRate()).isEqualByComparingTo("1.5");
    }
}
