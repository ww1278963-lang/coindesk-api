package com.example.coindesk.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CurrencyControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/api/currencies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].code").value("EUR"))
                .andExpect(jsonPath("$[2].code").value("USD"));
    }

    @Test
    void findByCode() throws Exception {
        mockMvc.perform(get("/api/currencies/USD"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.nameZh").value("美元"));
    }

    @Test
    void findByCodeNotFound() throws Exception {
        mockMvc.perform(get("/api/currencies/JPY"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("幣別不存在: JPY"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"jpy\",\"nameZh\":\"日圓\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("JPY"))
                .andExpect(jsonPath("$.nameZh").value("日圓"));
    }

    @Test
    void createDuplicate() throws Exception {
        mockMvc.perform(post("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"USD\",\"nameZh\":\"美金\"}"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("幣別已重複: USD"));
    }

    @Test
    void createInvalid() throws Exception {
        mockMvc.perform(post("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"US\",\"nameZh\":\"美元\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("code 必須為三個英文字母"));
    }

    @Test
    void createBrokenJson() throws Exception {
        mockMvc.perform(post("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("請求內容不是合法的 JSON"));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(put("/api/currencies/USD")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nameZh\":\"美金\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.nameZh").value("美金"));
    }

    @Test
    void updateNotFound() throws Exception {
        mockMvc.perform(put("/api/currencies/JPY")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nameZh\":\"日圓\"}"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateInvalid() throws Exception {
        mockMvc.perform(put("/api/currencies/USD")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nameZh\":\" \"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCurrency() throws Exception {
        mockMvc.perform(delete("/api/currencies/EUR"))
                .andDo(print())
                .andExpect(status().isNoContent());

        //刪完再查一次要查不到
        mockMvc.perform(get("/api/currencies/EUR"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(delete("/api/currencies/JPY"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
