package com.pratik.cash_rich_assignment.assignment.service;

import com.pratik.cash_rich_assignment.assignment.model.CoinDataRequest;
import com.pratik.cash_rich_assignment.assignment.repository.CoinDataRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class CoinmarketService {
    private final RestTemplate restTemplate;
    private final String apiKey;
    private final CoinDataRequestRepository coinDataRequestRepository;

    @Autowired
    public CoinmarketService(RestTemplate restTemplate, String coinMarketCapApiKey, CoinDataRequestRepository coinDataRequestRepository) {
        this.restTemplate = restTemplate;
        this.apiKey = coinMarketCapApiKey;
        this.coinDataRequestRepository = coinDataRequestRepository;
    }

    public String getCoinData(Long userId) {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=BTC,ETH,LTC";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        CoinDataRequest coinDataRequest = CoinDataRequest.builder()
                .userId(userId)
                .response(response.getBody())
                .timestamp(LocalDateTime.now())
                .build();

        coinDataRequestRepository.save(coinDataRequest);

        return response.getBody();
    }
}
