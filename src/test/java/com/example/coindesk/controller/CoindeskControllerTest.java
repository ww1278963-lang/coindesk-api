package com.example.coindesk.controller;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

//會連到coindesk要有網路
@SpringBootTest
@AutoConfigureMockMvc
class CoindeskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getOriginal() throws Exception {
        mockMvc.perform(get("/api/coindesk/original"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time.updatedISO").exists())
                .andExpect(jsonPath("$.bpi.USD.rate_float").exists());
    }

    @Test
    void getTransformed() throws Exception {
        mockMvc.perform(get("/api/coindesk/transformed"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time")
                        .value(matchesPattern("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}")))
                .andExpect(jsonPath("$.bpi.USD.nameZh").value("美元"))
                .andExpect(jsonPath("$.bpi.USD.rate").exists());
    }
}
