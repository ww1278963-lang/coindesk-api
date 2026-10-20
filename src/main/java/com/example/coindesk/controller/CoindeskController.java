package com.example.coindesk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.coindesk.dto.CoindeskResponse;
import com.example.coindesk.dto.RateResponse;
import com.example.coindesk.service.CoindeskService;

@RestController
@RequestMapping("/api/coindesk")
public class CoindeskController {

    private final CoindeskService coindeskService;

    public CoindeskController(CoindeskService coindeskService) {
        this.coindeskService = coindeskService;
    }

    @GetMapping("/original")
    public CoindeskResponse getOriginal() {
        return coindeskService.getOriginal();
    }

    @GetMapping("/transformed")
    public RateResponse getTransformed() {
        return coindeskService.getTransformed();
    }
}
