package com.javahk.project.finnhub.service;

import com.javahk.project.finnhub.exception.FinnhubException;
import com.javahk.project.finnhub.model.finnhub.resp.CompanyProfile2DTO;

public interface CompanyService {
    
    CompanyProfile2DTO getCompanyProfile(String symbol) throws FinnhubException;

    void deleteAll();
}
