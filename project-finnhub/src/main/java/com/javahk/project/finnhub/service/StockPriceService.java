package com.javahk.project.finnhub.service;

import com.javahk.project.finnhub.entity.StockPrice;
import com.javahk.project.finnhub.model.finnhub.resp.QuoteDTO;

public interface StockPriceService {
    QuoteDTO getQuote(String symbol);
    
    // StockPrice save(Long id, StockPrice stockprice);
    void savePrice(String symbol);
}
