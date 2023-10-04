package com.javahk.project.finnhub.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.javahk.project.finnhub.entity.StockSymbol;
import com.javahk.project.finnhub.infra.Protocol;
import com.javahk.project.finnhub.mapper.FinnhubMapper;
import com.javahk.project.finnhub.model.finnhub.resp.SymbolDTO;
import com.javahk.project.finnhub.repository.StockSymbolRepository;
import com.javahk.project.finnhub.service.StockSymbolService;

@Service
public class StockSymbolServiceImpl implements StockSymbolService {

    @Autowired
    private StockSymbolRepository symbolRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FinnhubMapper finnhubMapper;

    @Autowired
    @Qualifier(value = "finnhubToken")
    private String token;

    @Value(value = "${api.finnhub.domain}")
    private String domain;

    @Value(value = "${api.finnhub.base-url}")
    private String baseUrl;

    @Value(value = "${api.finnhub.endpoints.stock.symbol}")
    private String symbolEndpoint;

    @Override
    public List<SymbolDTO> getAllSymbols() {
        String url = UriComponentsBuilder.newInstance() //
                .scheme(Protocol.HTTPS.name()) //
                .host(domain) //
                .pathSegment(baseUrl) //
                .path(symbolEndpoint) //
                .queryParam("exchange", "US") //
                .queryParam("token", token) //
                .build() //
                .toUriString();
        System.out.println("url=" + url);

        SymbolDTO[] symbols = restTemplate.getForObject(url, SymbolDTO[].class);
        return Arrays.asList(symbols);
    }

    @Override
    public List<StockSymbol> saveAllSymbols() {
        List<SymbolDTO> symbols = getAllSymbols();
        List<StockSymbol> stockSymbols = symbols.stream() //
                .filter(s -> "Common Stock".equals(s.getType())) //
                .map(s -> finnhubMapper.map(s)) // convert to entity
                .collect(Collectors.toList());
        return symbolRepository.saveAll(stockSymbols);
    }

    // void deleteAll();
}
