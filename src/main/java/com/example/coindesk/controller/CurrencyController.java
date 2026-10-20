package com.example.coindesk.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.coindesk.dto.CreateCurrencyRequest;
import com.example.coindesk.dto.UpdateCurrencyRequest;
import com.example.coindesk.entity.Currency;
import com.example.coindesk.service.CurrencyService;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    
    private final CurrencyService currencyService;
    
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public List<Currency> findAll() {
        return currencyService.findAll();
    }

    @GetMapping("/{code}")
    public Currency findByCode(@PathVariable String code) {
        return currencyService.findByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Currency create(@Valid@RequestBody CreateCurrencyRequest request) {
        return currencyService.create(request);
    }

    @PutMapping("/{code}")
    public Currency update(@PathVariable String code, @Valid@RequestBody UpdateCurrencyRequest request) {
        return currencyService.update(code, request);
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String code) {
        currencyService.delete(code);
    }
}