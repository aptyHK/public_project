package com.javahk.project.finnhub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.javahk.project.finnhub.model.finnhub.resp.CompanyProfile2DTO;
import com.javahk.project.finnhub.service.CompanyService;

public interface DataOperation {
    
    @GetMapping(value = "/stock/{symbol}")
    @ResponseStatus(value = HttpStatus.OK)
    CompanyProfile2DTO getCompanyProfile2BySymbol(@PathVariable String symbol);

    @PostMapping(value = "/data/stock/{symbol}")
    @ResponseStatus(value = HttpStatus.OK)
    void save(@PathVariable String symbol);
}
