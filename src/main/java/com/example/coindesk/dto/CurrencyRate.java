package com.example.coindesk.dto;

import java.math.BigDecimal;

public class CurrencyRate {

    private final String code;

    private final String nameZh;

    private final BigDecimal rate;

    public CurrencyRate(String code, String nameZh, BigDecimal rate) {
        this.code = code;
        this.nameZh = nameZh;
        this.rate = rate;
    }

    public String getCode() {
        return code;
    }

    public String getNameZh() {
        return nameZh;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
