package com.example.springbasics.service.impl;

import com.example.springbasics.entity.Trade;
import com.example.springbasics.models.TradeSpecifications;
import com.example.springbasics.repository.TradeRepository;
import com.example.springbasics.service.TradeService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }


    @Override
    public Trade createTrade(Trade trade) throws BadRequestException {
        if (!isTradeQuantityBetween1And100(trade)) {
            throw new BadRequestException("quantity has to be between 1 and 1000");
        }
        if (!isPriceAbove01(trade)) {
            throw new BadRequestException("price has to be above 0.01");
        }
        return tradeRepository.save(trade);
    }

    @Override
    public List<Trade> searchTrade(Float price, Integer quantity) {
        Specification<Trade> spec = Specification.where(TradeSpecifications.withPrice(price))
                .and(TradeSpecifications.withQuantity(quantity));

        return tradeRepository.findAll(spec);
    }

    public boolean isTradeQuantityBetween1And100(Trade trade) {
        return trade.getQuantity() >= 1 && trade.getQuantity() <= 1000;
    }

    public boolean isPriceAbove01(Trade trade) {
        return trade.getPrice() > 0.1;
    }


}
