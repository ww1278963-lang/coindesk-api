package com.example.coindesk.service;

import com.example.coindesk.dto.CoindeskResponse;
import com.example.coindesk.dto.RateResponse;

public interface CoindeskService {

    CoindeskResponse getOriginal();

    RateResponse getTransformed();
}
