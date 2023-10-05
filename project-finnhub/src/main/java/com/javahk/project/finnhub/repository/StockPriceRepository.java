package com.javahk.project.finnhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javahk.project.finnhub.entity.StockPrice;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long>{
    
}
