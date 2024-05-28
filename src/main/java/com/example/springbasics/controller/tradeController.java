package com.example.springbasics.controller;

import com.example.springbasics.entity.Trade;
import com.example.springbasics.service.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class tradeController {

    final TradeService tradeService;

    public tradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping()
    public ResponseEntity<Trade> createTrade(@RequestBody Trade trade) throws Exception {
        tradeService.createTrade(trade);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Trade>> searchTrades(@RequestParam(required = false) Float price, @RequestParam(required = false) Integer quantity) {
        return new ResponseEntity<>(tradeService.searchTrade(price, quantity), HttpStatus.FOUND);
    }

}
