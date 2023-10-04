package com.javahk.project.finnhub.service;

import com.javahk.project.finnhub.model.finnhub.resp.CompanyProfile2DTO;

public interface CompanyService {
    
    CompanyProfile2DTO getCompanyProfile2(String Symbol);

    void save(String symbol);
}
