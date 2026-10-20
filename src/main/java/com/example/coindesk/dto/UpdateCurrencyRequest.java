package com.example.coindesk.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateCurrencyRequest {

    @NotBlank
    @Size(max = 50)
    private String nameZh;

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }
}