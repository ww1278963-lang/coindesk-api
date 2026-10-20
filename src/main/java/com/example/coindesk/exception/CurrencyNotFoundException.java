package com.example.coindesk.exception;

public class CurrencyNotFoundException extends RuntimeException {

    public CurrencyNotFoundException(String code) {
            super("幣別不存在: " + code);
    }
}