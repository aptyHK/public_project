package com.javahk.project.finnhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.javahk.project.finnhub.entity.StockPrice;
import com.javahk.project.finnhub.infra.Protocol;
import com.javahk.project.finnhub.mapper.FinnhubMapper;
import com.javahk.project.finnhub.model.finnhub.resp.QuoteDTO;
import com.javahk.project.finnhub.repository.StockPriceRepository;
import com.javahk.project.finnhub.service.StockPriceService;

@Service
public class StockPriceServiceImpl implements StockPriceService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Autowired
    private FinnhubMapper finnhubMapper;

    @Autowired
    @Qualifier(value = "finnhubToken")
    private String token;

    @Value(value = "${api.finnhub.domain}")
    private String domain;

    @Value(value = "${api.finnhub.base-url}")
    private String baseUrl;

    @Value(value = "${api.finnhub.endpoints.stock.quote}")
    private String quoteEndpoint;

    @Override
    public QuoteDTO getQuote(String symbol) {
        String url = UriComponentsBuilder.newInstance() //
            .scheme(Protocol.HTTPS.name().toLowerCase()) //
            .host(domain) // 
            .pathSegment(baseUrl) //
            .path(quoteEndpoint) //
            .queryParam("symbol", symbol) //
            .queryParam("token", token) //
            .build() //
            .toUriString();
        
        return restTemplate.getForObject(url, QuoteDTO.class);
    }

    @Override
    public void savePrice(String symbol) {
        QuoteDTO quote = this.getQuote(symbol);
        StockPrice stockPrice = finnhubMapper.map(quote);
        stockPriceRepository.save(stockPrice);
    }
}
