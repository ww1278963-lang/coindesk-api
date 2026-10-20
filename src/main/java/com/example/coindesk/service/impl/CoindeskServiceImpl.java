package com.example.coindesk.service.impl;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.example.coindesk.client.CoindeskClient;
import com.example.coindesk.dto.CoindeskBpi;
import com.example.coindesk.dto.CoindeskResponse;
import com.example.coindesk.dto.CurrencyRate;
import com.example.coindesk.dto.RateResponse;
import com.example.coindesk.entity.Currency;
import com.example.coindesk.repository.CurrencyRepository;
import com.example.coindesk.service.CoindeskService;

@Service
public class CoindeskServiceImpl implements CoindeskService {

    private final CoindeskClient coindeskClient;

    private final CurrencyRepository currencyRepository;

    public CoindeskServiceImpl(CoindeskClient coindeskClient, CurrencyRepository currencyRepository) {
        this.coindeskClient = coindeskClient;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public CoindeskResponse getOriginal() {
        return coindeskClient.fetch();
    }

    @Override
    public RateResponse getTransformed() {
        CoindeskResponse source = coindeskClient.fetch();

        LocalDateTime time = OffsetDateTime.parse(source.getTime().getUpdatedISO())
                .atZoneSameInstant(ZoneId.of("Asia/Taipei"))
                .toLocalDateTime();

        Map<String, String> nameMap = new HashMap<>();
        for (Currency currency : currencyRepository.findAll()) {
            nameMap.put(currency.getCode(), currency.getNameZh());
        }

        Map<String, CurrencyRate> bpi = new TreeMap<>();
        for (CoindeskBpi item : source.getBpi().values()) {
            String code = item.getCode();
            bpi.put(code, new CurrencyRate(code, resolveNameZh(code, nameMap), item.getRateFloat()));
        }

        return new RateResponse(time, bpi);
    }

    //查中文若找不到就給null
    private String resolveNameZh(String code, Map<String, String> nameMap) {
        String nameZh = nameMap.get(code);
        if (nameZh != null) {
            return nameZh;
        }
        try {
            return java.util.Currency.getInstance(code).getDisplayName(Locale.TAIWAN);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
