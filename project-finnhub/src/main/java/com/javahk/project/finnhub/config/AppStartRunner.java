package com.javahk.project.finnhub.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.javahk.project.finnhub.entity.Stock;
import com.javahk.project.finnhub.entity.StockPrice;
import com.javahk.project.finnhub.exception.FinnhubException;
import com.javahk.project.finnhub.mapper.FinnhubMapper;
import com.javahk.project.finnhub.model.finnhub.resp.CompanyProfile2DTO;
import com.javahk.project.finnhub.model.finnhub.resp.QuoteDTO;
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
    public static final List<String> stocksInventory = List.of("GOOGL", "AMZN", "AAPL", "MSFT");

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
        // filter the symbol listed on stocksInventory
        List<SymbolDTO> symbols = stockSymbolService.getStockSymbol().stream()
                .filter(symbol -> stocksInventory.contains(symbol.getSymbol()))
                .collect(Collectors.toList());
        System.out.println("Symbols are inserted, Symbol amounts = " + symbols.size());

        // 2.
        // for each symbol,covert to SymbolDTO to StockSymbol (the entity)
        // and than through service layer to do symbolRepository.saveAll(stockSymbols)
        // save the StockSymbol to database
        // the save method in service layer, will put input as DTO and return as entity
        stockSymbolService.save(symbols).stream() // as the return is list, can stream directly
                .forEach(symbol -> {
                    try {
                        // 3. Use each symbol to get dedicate company profile
                        CompanyProfile2DTO profile = companyService.getCompanyProfile(symbol.getSymbol());
                        // 3.1 convert it to entity Stock
                        Stock stock = finnhubMapper.map(profile);
                        // 3.2 embed the symbol id to the stock entity, as foreign key
                        // also set stock status to 'N', mean new added to db
                        stock.setStockSymbol(symbol);
                        stock.setStockStatus('N'); 
                        // 3.3 the stock entity completed, save it to the database
                        // to specifically use a new object reference to carry the same stock, because:
                        // i) there is a return value from the save method, in java, you can choose to
                        // ignore return value or not
                        // but here I have further use on the Stock so I use an object reference to
                        // carry it
                        // ii) in design aspect, if other method need to reuse the object, is good to
                        // know using which stage of the object
                        // also is good for make change between method in the future
                        Stock storedStock = companyService.save(stock);
                        System.out.println("Added stock, symbol=" + symbol.getSymbol());

                        // 4. Use each symbol to get dedicate quote
                        QuoteDTO quote = stockPriceService.getQuote(symbol.getSymbol());
                        // 4.1 convert it to entity StockPrice
                        StockPrice stockPrice = finnhubMapper.map(quote);
                        // 4.2 embed the storedstock completed earlier, use as foriegn key (1 stock can
                        // have many record of price by different time)
                        stockPrice.setStock(storedStock);
                        // 4.3 save the completed StockPrice entity to the database
                        // the difference with 3.3 is, currently I don't have any method need to call
                        // stockPrice in this state
                        // so is not necessary to use an extra object reference to store it
                        stockPriceService.save(stockPrice);
                        System.out.println("Added quote, symbol=" + symbol.getSymbol());

                        // 4. Get Symbol (not yet done, for future use with the annotation folder)
                        // availableStockList.add(symbol.getSymbol());

                    } catch (FinnhubException e) {
                        // just tell encounter issue cause by finnhub, but no action
                        // (per symbol)
                        System.out.println("RestClientException: Symbol" + symbol.getSymbol());
                    }
                });
        System.out.println("Stocks in Inventory are inserted.");
        System.out.println("CommandLineRunner Completed");
    }
}
