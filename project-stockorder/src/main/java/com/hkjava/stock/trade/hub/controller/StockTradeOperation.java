package com.hkjava.stock.trade.hub.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.hkjava.stock.trade.hub.dto.req.PlaceOrderDTO;
import com.hkjava.stock.trade.hub.dto.resp.OrderBookDTO;
import com.hkjava.stock.trade.hub.model.Order;

public interface StockTradeOperation {

  @GetMapping(value = "/stock/orderbook")
  @ResponseStatus(value = HttpStatus.OK)
  OrderBookDTO orderBook(String symbol);

  @PostMapping(value = "/stock/order/userid/{userId}/symbol/{symbol}")
  @ResponseStatus(value = HttpStatus.OK)
  Order order(@RequestParam String userId, @RequestParam String symbol,
      @RequestBody PlaceOrderDTO tradeDTO);

}
