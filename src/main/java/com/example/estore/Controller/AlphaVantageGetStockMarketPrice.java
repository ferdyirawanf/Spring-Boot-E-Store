package com.example.estore.Controller;

import com.example.estore.DTO.response.RenderJson;
import com.example.estore.service.AlphaVantageGetStockMarketPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/alphavantage")
public class AlphaVantageGetStockMarketPrice {
    private final AlphaVantageGetStockMarketPriceService alphaVantageGetStockMarketPrice;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "function", required = false) String function,
            @RequestParam(name = "symbol", required = false) String symbol,
            @RequestParam(name = "apikey", required = false) String apikey
    )
    {
        return RenderJson.basicFormat(alphaVantageGetStockMarketPrice.index(function, symbol, apikey), HttpStatus.OK);
    }
}
