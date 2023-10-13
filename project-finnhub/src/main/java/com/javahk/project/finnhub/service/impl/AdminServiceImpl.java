package com.javahk.project.finnhub.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javahk.project.finnhub.entity.Stock;
import com.javahk.project.finnhub.entity.StockPrice;
import com.javahk.project.finnhub.exception.FinnhubException;
import com.javahk.project.finnhub.mapper.FinnhubMapper;
import com.javahk.project.finnhub.model.finnhub.resp.CompanyProfile2DTO;
import com.javahk.project.finnhub.model.finnhub.resp.QuoteDTO;
import com.javahk.project.finnhub.repository.StockPriceRepository;
import com.javahk.project.finnhub.repository.StockRepository;
import com.javahk.project.finnhub.repository.StockSymbolRepository;
import com.javahk.project.finnhub.service.AdminService;
import com.javahk.project.finnhub.service.CompanyService;
import com.javahk.project.finnhub.service.StockPriceService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    CompanyService companyService;

    @Autowired
    StockPriceService stockPriceService;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockPriceRepository stockPriceRepository;

    @Autowired
    StockSymbolRepository stockSymbolRepository;

    @Autowired
    FinnhubMapper finnhubMapper;

    @Override
    public void refresh() throws FinnhubException {
        // get all the symbol that appointed to obtain data
        stockSymbolRepository.findAll().stream() //
                .forEach(symbol -> {
                    try {
                        // retrieve the current company profile from finnhub
                        CompanyProfile2DTO newProfile = companyService.getCompanyProfile(symbol.getSymbol());

                        // get old stock from repository
                        Optional<Stock> oldStock = stockRepository.findByStockSymbol(symbol);

                        // extract Optional<Stock> if oldStock is not null
                        // in normal case, should be not null as already have stock inputted in db at
                        // app start runner
                        // set the current stock info to form a new stock entity
                        if (oldStock.isPresent()) {
                            Stock stock = oldStock.get();
                            stock.setCountry(newProfile.getCountry());
                            stock.setCompanyName(newProfile.getCompanyName());
                            stock.setLogo(newProfile.getLogo());
                            stock.setMarketCap(newProfile.getMarketCap());
                            stock.setCurrency(newProfile.getCurrency());

                            // If normal response from finnhub, findById, set statuss to active
                            // If abnormal response, patch Entity status to 'I'
                            if (newProfile != null && newProfile.getTicker().equals(symbol.getSymbol())) {
                                stock.setStockStatus('A'); // set to active
                            } else {
                                stock.setStockStatus('I'); // set to inactive
                            }
                            // put the updated entity to DB
                            stockRepository.save(stock);
                            System.out.println("refreshed stock, symbol=" + symbol.getSymbol() + "status = "
                                    + stock.getStockStatus());

                            // get stock price and save a new record of price into db
                            // if no stock, even can retrieve stock price, ignore
                            // because stockprice do set stock_id from entity stock as foriegn key, and
                            // column set to nullable
                            // and is okay that can get company profile but no price (just 1 less price
                            // point on db, but no affect on counting average)
                            // so no need extra checking on stock price is present or not
                            QuoteDTO quote = stockPriceService.getQuote(symbol.getSymbol());
                            StockPrice stockPrice = finnhubMapper.map(quote);
                            stockPrice.setStock(stock);
                            stockPriceRepository.save(stockPrice);
                            System.out.println("refreshed price, symbol=" + symbol.getSymbol());

                        } else if (!oldStock.isPresent() && newProfile != null
                                && newProfile.getTicker().equals(symbol.getSymbol())) {
                            // situation that cannot form stock entity during app start runner / you can
                            // first find the profile after n times refresh (e.g. finnhub issue)
                            // and can able to retrieve finnhub data at current refresh
                            Stock newStock = finnhubMapper.map(newProfile);
                            newStock.setStockSymbol(symbol);
                            newStock.setStockStatus('N');
                            stockRepository.save(newStock);
                            System.out.println("Added stock, symbol=" + symbol.getSymbol());
                            QuoteDTO quote = stockPriceService.getQuote(symbol.getSymbol());
                            StockPrice newStockPrice = finnhubMapper.map(quote);
                            newStockPrice.setStock(newStock);
                            stockPriceRepository.save(newStockPrice);
                            System.out.println("Added price, symbol=" + symbol.getSymbol());
                        } else {
                            System.out.println(symbol.getSymbol() + " is NOT FOUND.");
                        }
                    } catch (FinnhubException e) {
                        System.out.println("RestClientException: Symbol" + symbol.getSymbol());
                    }
                });
    }
}
