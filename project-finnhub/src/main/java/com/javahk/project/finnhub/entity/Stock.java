package com.javahk.project.finnhub.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "finnhub_stocks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "ipo_date")
    private LocalDate ipoDate;

    private String logo;

    @Column(name = "market_cap", columnDefinition = "NUMERIC(15,2)")
    private double marketCap;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status", columnDefinition = "VARCHAR(1)") // 'A', 'I'
    private Character stockStatus;

    // assume like select * from stock s, stocksymbol ss where s.symbol_id = ss.id
    // actually symbol_id is not exist in this Stock.java, but it do set symbol on the appstartrunner
    // @OneToOne
    // @JoinColumn(name = "symbol_id", nullable = false)
    // private StockSymbol stockSymbol;

}
