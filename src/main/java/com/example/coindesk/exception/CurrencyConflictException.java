package com.example.coindesk.exception;

public class CurrencyConflictException extends RuntimeException {

    public CurrencyConflictException(String code) {
            super("幣別已重複: " + code);
    }
}