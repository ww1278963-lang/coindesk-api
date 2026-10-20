package com.example.coindesk.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateCurrencyRequest {
    
    @NotBlank
    @Pattern(regexp = "[A-Za-z]{3}", message = "必須為三個英文字母")
    private String code;

    @NotBlank
    @Size(max = 50)
    private String nameZh;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }
}