package com.javahk.project.finnhub.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javahk.project.finnhub.controller.DataOperation;
import com.javahk.project.finnhub.model.finnhub.resp.CompanyProfile2DTO;
import com.javahk.project.finnhub.service.CompanyService;

@RestController
@RequestMapping(value = "/api/v1")
public class DataController implements DataOperation {

    @Autowired
    CompanyService companyService;

    @Override
    public CompanyProfile2DTO getCompanyProfile2BySymbol(String symbol) {
        return companyService.getCompanyProfile2(symbol);
    }

    @Override
    public void save(String symbol) {
        companyService.save(symbol);
    }
}
