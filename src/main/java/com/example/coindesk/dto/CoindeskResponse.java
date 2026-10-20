package com.example.coindesk.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//防止突然新增欄位
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindeskResponse {

    private CoindeskTime time;

    private String disclaimer;

    private String chartName;

    private Map<String, CoindeskBpi> bpi;

    public CoindeskTime getTime() {
        return time;
    }

    public void setTime(CoindeskTime time) {
        this.time = time;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public Map<String, CoindeskBpi> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, CoindeskBpi> bpi) {
        this.bpi = bpi;
    }
}
