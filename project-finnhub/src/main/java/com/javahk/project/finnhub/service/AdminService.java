package com.javahk.project.finnhub.service;

import com.javahk.project.finnhub.exception.FinnhubException;

public interface AdminService {
    
    void refresh() throws FinnhubException;
}
