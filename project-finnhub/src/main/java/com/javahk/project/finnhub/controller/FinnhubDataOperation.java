package com.javahk.project.finnhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.javahk.project.finnhub.exception.FinnhubException;
import com.javahk.project.finnhub.model.finnhub.resp.CompanyProfile2DTO;
import com.javahk.project.finnhub.model.finnhub.resp.QuoteDTO;
import com.javahk.project.finnhub.model.finnhub.resp.SymbolDTO;
import com.javahk.project.finnhub.service.CompanyService;

public interface FinnhubDataOperation {

    @GetMapping(value = "/getStock")
    @ResponseStatus(value = HttpStatus.OK)
    CompanyProfile2DTO getCompanyProfile( // ?symbol=xxxx
            @RequestParam("symbol") String symbol) throws FinnhubException;
    // FinnhubException should caught by GlobalExceptionHandler, so no need to set a try catch

    @GetMapping(value = "/getQuote")
    @ResponseStatus(value = HttpStatus.OK)
    QuoteDTO getQuote(@RequestParam("symbol") String symbol)
            throws FinnhubException;

    @GetMapping(value = "/stockSymbols")
    @ResponseStatus(value = HttpStatus.OK)
    List<SymbolDTO> getStockSymbol() throws FinnhubException;
}
