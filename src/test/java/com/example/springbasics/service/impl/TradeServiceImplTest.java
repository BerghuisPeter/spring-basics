package com.example.springbasics.service.impl;

import com.example.springbasics.entity.Trade;
import com.example.springbasics.models.BUY_SELL;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class TradeServiceImplTest {

    @InjectMocks
    TradeServiceImpl tradeService;

    @Test
    void trade_default_values() {
        Trade trade = new Trade();
        trade.setBuySell(BUY_SELL.B);
        trade.setPrice(5F);
        trade.setQuantity(3);

        assertEquals("JP", trade.getOriginCountry());
        assertEquals("JPY", trade.getCurrency());
    }


    @Test
    public void testIsTradeQuantityBetween1And100() {
        Trade newTrade = new Trade();
        newTrade.setQuantity(0);
        newTrade.setPrice(5F);
        newTrade.setBuySell(BUY_SELL.B);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            tradeService.createTrade(newTrade);
        });

        String expectedMessage = "quantity has to be between 1 and 1000";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testIsPriceAbove01() {
        Trade newTrade = new Trade();
        newTrade.setQuantity(6);
        newTrade.setPrice(0F);
        newTrade.setBuySell(BUY_SELL.B);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            tradeService.createTrade(newTrade);
        });

        String expectedMessage = "price has to be above 0.01";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}