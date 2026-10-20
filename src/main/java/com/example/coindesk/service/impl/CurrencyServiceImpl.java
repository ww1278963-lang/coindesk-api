package com.example.coindesk.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.coindesk.dto.CreateCurrencyRequest;
import com.example.coindesk.dto.UpdateCurrencyRequest;
import com.example.coindesk.entity.Currency;
import com.example.coindesk.exception.CurrencyConflictException;
import com.example.coindesk.exception.CurrencyNotFoundException;
import com.example.coindesk.repository.CurrencyRepository;
import com.example.coindesk.service.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll(Sort.by("code"));
    }

    @Override
    public Currency findByCode(String code) {
        return currencyRepository.findById(code.toUpperCase())
                        .orElseThrow(() -> new CurrencyNotFoundException(code));
    }

    @Override
    public Currency create(CreateCurrencyRequest request) {
        String code = request.getCode().toUpperCase();
        if (currencyRepository.existsById(code)) {
                throw new CurrencyConflictException(code);
        }
        return currencyRepository.save(new Currency(code, request.getNameZh()));
    }

    @Override
    public Currency update(String code, UpdateCurrencyRequest request) {
        Currency currency = currencyRepository.findById(code.toUpperCase())
                        .orElseThrow(() -> new CurrencyNotFoundException(code));
        currency.setNameZh(request.getNameZh());
        return currencyRepository.save(currency);
    }

    @Override
    public void delete(String code) {
        Currency currency = currencyRepository.findById(code.toUpperCase())
                        .orElseThrow(() -> new CurrencyNotFoundException(code));
        currencyRepository.delete(currency);
    }
}