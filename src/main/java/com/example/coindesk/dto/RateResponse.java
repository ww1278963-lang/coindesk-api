package com.example.coindesk.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RateResponse {

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private final LocalDateTime time;

    //跟原始結構一樣 幣別代碼 → 該幣別資料
    private final Map<String, CurrencyRate> bpi;

    public RateResponse(LocalDateTime time, Map<String, CurrencyRate> bpi) {
        this.time = time;
        this.bpi = bpi;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Map<String, CurrencyRate> getBpi() {
        return bpi;
    }
}
