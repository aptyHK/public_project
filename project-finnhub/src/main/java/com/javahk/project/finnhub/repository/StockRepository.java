package com.javahk.project.finnhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javahk.project.finnhub.entity.Stock;
import com.javahk.project.finnhub.entity.StockSymbol;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    
    Optional<Stock> findByStockSymbol(StockSymbol stockSymbol);
}
