package com.example.coindesk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//防止突然新增欄位
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindeskTime {

    private String updated;

    private String updatedISO;

    private String updateduk;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdatedISO() {
        return updatedISO;
    }

    public void setUpdatedISO(String updatedISO) {
        this.updatedISO = updatedISO;
    }

    public String getUpdateduk() {
        return updateduk;
    }

    public void setUpdateduk(String updateduk) {
        this.updateduk = updateduk;
    }
}
