package com.javahk.project.finnhub.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.javahk.project.finnhub.exception.FinnhubException;
import com.javahk.project.finnhub.mapper.FinnhubMapper;
import com.javahk.project.finnhub.model.finnhub.resp.SymbolDTO;
import com.javahk.project.finnhub.service.CompanyService;
import com.javahk.project.finnhub.service.StockPriceService;
import com.javahk.project.finnhub.service.StockSymbolService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j // a lombok annotation, to apply log. method
@Profile("!test") // 目的：防止database 有野，要確保isEmpty
public class AppStartRunner implements CommandLineRunner {

    // The list of company that I want to apply on this project
    // set to final so no one can update it unless re-compile a new jar to run again
    // if not set final, can write an api to update the list
    public static final List<String> stocksInventory = List.of("GOOGL", "AMZN", "AAPL", "MSFT", "FB");

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StockSymbolService stockSymbolService;

    @Autowired
    private StockPriceService stockPriceService;

    @Autowired
    private FinnhubMapper finnhubMapper;

    @Override
    public void run(String... args) throws FinnhubException {

        // Before Server Start:
        // 0.
        // ask service layer to control repository layer to clear
        // for ddl-update setting set in the yml.
        // so can delete the previous data and load new data (good for testing)
        stockPriceService.deleteAll();
        companyService.deleteAll();
        stockSymbolService.deleteAll();

        // 1.
        // call api to get all symbols
        List<SymbolDTO> symbols = stockSymbolService.getStockSymbol().stream()
            .filter(symbol -> stocksInventory.contains(symbol.getSymbol()))
            .collect(Collectors.toList());
        System.out.println("Symbols are inserted, Symbol amounts = " + symbols.size());
    }
}
