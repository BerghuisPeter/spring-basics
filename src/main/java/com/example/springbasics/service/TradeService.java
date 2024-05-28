package com.example.springbasics.service;

import com.example.springbasics.entity.Trade;

import java.util.List;

public interface TradeService {
    Trade createTrade(Trade trade) throws Exception;
    List<Trade> searchTrade(Float price, Integer quantity);
}
