package com.example.estore.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AlphaVantageGetStockMarketPriceService {
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://www.alphavantage.co/query?function=%s&symbol=%s&apikey=%s";

    public String index(String function, String symbol, String apikey) {
        String url = baseUrl.formatted(function, symbol, apikey);
        return restTemplate.getForObject(url, String.class);
    }
}
