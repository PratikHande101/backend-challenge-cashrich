package com.pratik.cash_rich_assignment.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinmarketService {
    private final RestTemplate restTemplate;
    private final String apiKey;

    @Autowired
    public CoinmarketService(RestTemplate restTemplate, String coinMarketCapApiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = coinMarketCapApiKey;
    }

    public String getCoinData() {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=BTC,ETH,LTC";
        return restTemplate.getForObject(url, String.class, apiKey);
    }
}
