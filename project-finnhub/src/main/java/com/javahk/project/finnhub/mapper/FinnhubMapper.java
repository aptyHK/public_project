package com.javahk.project.finnhub.mapper;

import org.springframework.stereotype.Component;

import com.javahk.project.finnhub.entity.Stock;
import com.javahk.project.finnhub.entity.StockSymbol;
import com.javahk.project.finnhub.model.finnhub.resp.CompanyProfile2DTO;
import com.javahk.project.finnhub.model.finnhub.resp.SymbolDTO;

@Component
public class FinnhubMapper {

    public Stock map(CompanyProfile2DTO profile) {
        return Stock.builder() //
                .country(profile.getCountry()) //
                .companyName(profile.getCompanyName()) //
                .logo(profile.getLogo()) //
                .marketCap(profile.getMarketCap()) //
                .currency(profile.getCurrency()) //
                .build();
    }

    public StockSymbol map(SymbolDTO symbol) {
        return StockSymbol.builder() //
            .symbol(symbol.getSymbol())
            .build();
    }
}
