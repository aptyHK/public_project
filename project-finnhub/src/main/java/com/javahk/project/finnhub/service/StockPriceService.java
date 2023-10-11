package com.javahk.project.finnhub.service;

import com.javahk.project.finnhub.entity.StockPrice;
import com.javahk.project.finnhub.exception.FinnhubException;
import com.javahk.project.finnhub.model.finnhub.resp.QuoteDTO;

public interface StockPriceService {

    QuoteDTO getQuote(String symbol) throws FinnhubException;

    void deleteAll();
}
