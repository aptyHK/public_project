package com.javahk.project.finnhub.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javahk.project.finnhub.controller.FinnhubDataOperation;
import com.javahk.project.finnhub.exception.FinnhubException;
import com.javahk.project.finnhub.mapper.FinnhubMapper;
import com.javahk.project.finnhub.model.finnhub.resp.CompanyProfile2DTO;
import com.javahk.project.finnhub.model.finnhub.resp.QuoteDTO;
import com.javahk.project.finnhub.model.finnhub.resp.SymbolDTO;
import com.javahk.project.finnhub.service.CompanyService;
import com.javahk.project.finnhub.service.StockPriceService;
import com.javahk.project.finnhub.service.StockSymbolService;

@RestController
@RequestMapping(value = "/api/v1")
public class FinnhubDataController implements FinnhubDataOperation {

    @Autowired
    FinnhubMapper finnhubMapper;

    @Autowired
    CompanyService companyService;

    @Autowired
    StockSymbolService stockSymbolService;

    @Autowired
    StockPriceService stockPriceService;

    @Override
    public CompanyProfile2DTO getCompanyProfile(String symbol) throws FinnhubException {
        return companyService.getCompanyProfile(symbol);
    }

    @Override
    public QuoteDTO getQuote(String symbol) throws FinnhubException {
        return stockPriceService.getQuote(symbol);
    }

    @Override
    public List<SymbolDTO> getStockSymbol() throws FinnhubException {
        return stockSymbolService.getStockSymbol();
    }

}
