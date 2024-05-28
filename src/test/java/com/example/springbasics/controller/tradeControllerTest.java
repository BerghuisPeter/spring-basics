package com.example.springbasics.controller;

import com.example.springbasics.entity.Trade;
import com.example.springbasics.models.BUY_SELL;
import com.example.springbasics.repository.TradeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class tradeControllerTest {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createTrade() throws Exception {
        Trade trade = new Trade();
        trade.setBuySell(BUY_SELL.B);
        trade.setPrice(5F);
        trade.setQuantity(3);

        ObjectMapper mapper = new ObjectMapper();
        String StringTrade = mapper.writeValueAsString(trade);

        mockMvc.perform(post("/trades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StringTrade))

                .andExpect(status().isCreated());
    }

    @Test
    void getAllTrades() throws Exception {
        Trade trade = new Trade();
        trade.setBuySell(BUY_SELL.B);
        trade.setPrice(5F);
        trade.setQuantity(3);

        tradeRepository.save(trade);

        mockMvc.perform(get("/trades").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.*", hasSize(greaterThan(0))))
                .andReturn();
    }

    @Test
    void searchTrades() throws Exception {
        Trade trade = new Trade();
        trade.setBuySell(BUY_SELL.B);
        trade.setPrice(666F);
        trade.setQuantity(3);
        tradeRepository.save(trade);

        trade.setId(null);
        trade.setQuantity(9);
        tradeRepository.save(trade);

        trade.setId(null);
        trade.setPrice(669F);
        trade.setQuantity(9);
        tradeRepository.save(trade);

        mockMvc.perform(get("/trades?price=666").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andReturn();

        mockMvc.perform(get("/trades?quantity=9").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andReturn();

        mockMvc.perform(get("/trades?quantity=9&price=666").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andReturn();
    }
}