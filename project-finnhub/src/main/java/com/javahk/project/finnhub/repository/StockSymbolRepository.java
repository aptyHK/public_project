package com.javahk.project.finnhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.javahk.project.finnhub.entity.StockSymbol;

@Repository
public interface StockSymbolRepository extends JpaRepository<StockSymbol, Integer> {
    
}