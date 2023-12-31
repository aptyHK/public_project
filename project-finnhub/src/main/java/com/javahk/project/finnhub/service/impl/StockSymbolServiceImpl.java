package com.javahk.project.finnhub.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.javahk.project.finnhub.entity.StockSymbol;
import com.javahk.project.finnhub.exception.FinnhubException;
import com.javahk.project.finnhub.infra.Code;
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
    public List<SymbolDTO> getStockSymbol() throws FinnhubException {
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

        try {
            SymbolDTO[] symbols = restTemplate.getForObject(url, SymbolDTO[].class);
            return Arrays.asList(symbols);
        } catch (RestClientException e) {
            throw new FinnhubException(Code.FINNHUB_SYMBOL_NOTFOUND);
        }
    }

    @Override
    public List<StockSymbol> save(List<SymbolDTO> symbols) {
        List<StockSymbol> stockSymbols = symbols.stream() //
                // there are many types of stock in US exchange, now we only filter the type common stock
                .filter(s -> "Common Stock".equals(s.getType()))
                .map(s -> finnhubMapper.map(s)) // convert to entity
                .collect(Collectors.toList());
        return symbolRepository.saveAll(stockSymbols);
    }

    @Override
    public void deleteAll() {
        symbolRepository.deleteAll();
    }
}
