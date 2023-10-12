package com.javahk.project.finnhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.javahk.project.finnhub.entity.Stock;
import com.javahk.project.finnhub.exception.FinnhubException;
import com.javahk.project.finnhub.infra.Code;
import com.javahk.project.finnhub.infra.Protocol;
import com.javahk.project.finnhub.mapper.FinnhubMapper;
import com.javahk.project.finnhub.model.finnhub.resp.CompanyProfile2DTO;
import com.javahk.project.finnhub.repository.StockRepository;
import com.javahk.project.finnhub.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private FinnhubMapper finnhubMapper;

    @Autowired
    @Qualifier(value = "finnhubToken")
    private String token;

    @Value(value = "${api.finnhub.domain}")
    private String domain;

    @Value(value = "${api.finnhub.base-url}")
    private String baseUrl;

    @Value(value = "${api.finnhub.endpoints.stock.profile2}")
    private String companyProfile2Endpoint;

    @Override
    public CompanyProfile2DTO getCompanyProfile(String symbol) throws FinnhubException {
        String url = UriComponentsBuilder.newInstance() //
                .scheme(Protocol.HTTPS.name().toLowerCase()) // for testing convinience
                .host(domain) //
                .pathSegment(baseUrl) //
                .path(companyProfile2Endpoint) //
                .queryParam("symbol", symbol) //
                .queryParam("token", token) //
                .build() //
                .toUriString();

        try {
            CompanyProfile2DTO companyProfile = restTemplate.getForObject(url, CompanyProfile2DTO.class);
            return companyProfile;
        } catch (RestClientException e) { // can test by set endpoint value incorrectly in yml
            throw new FinnhubException(Code.FINNHUB_PROFILE2_NOTFOUND);
        }

        // the thrown Exception should catch either by the controller / app start runner
    }

    @Override
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public void deleteAll() {
        stockRepository.deleteAll();
    }
}
