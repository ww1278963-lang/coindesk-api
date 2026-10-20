package com.example.coindesk.service;

import java.util.List;

import com.example.coindesk.dto.CreateCurrencyRequest;
import com.example.coindesk.dto.UpdateCurrencyRequest;
import com.example.coindesk.entity.Currency;

public interface CurrencyService {

    List<Currency> findAll();
    
    Currency findByCode(String code);

    Currency create(CreateCurrencyRequest request);

    Currency update(String code, UpdateCurrencyRequest request);

    void delete(String code);
}