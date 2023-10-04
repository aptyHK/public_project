package com.javahk.project.finnhub.service;

import java.util.List;

import com.javahk.project.finnhub.entity.StockSymbol;
import com.javahk.project.finnhub.model.finnhub.resp.SymbolDTO;

public interface StockSymbolService {
    List<SymbolDTO> getAllSymbols();

    List<StockSymbol> save(List<SymbolDTO> symbols);
    
    // void deleteAll();
}
