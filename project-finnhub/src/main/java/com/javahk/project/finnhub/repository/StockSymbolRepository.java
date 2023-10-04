package com.javahk.project.finnhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javahk.project.finnhub.entity.StockSymbol;

public interface StockSymbolRepository extends JpaRepository<StockSymbol, Integer> {
    
}
