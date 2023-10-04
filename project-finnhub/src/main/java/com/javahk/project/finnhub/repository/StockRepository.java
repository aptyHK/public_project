package com.javahk.project.finnhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javahk.project.finnhub.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    
}
