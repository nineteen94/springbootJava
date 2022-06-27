package com.vishaldhawan.pilot.currencyconversionservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

//    Change Kubernetes
    private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    @GetMapping(path = "currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        logger.info("calculateCurrencyConversion called with {} to {}, quantity {}", from, to, quantity);

        String uri = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity(
                uri, CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
        return currencyConversion;
    }

    @GetMapping(path = "currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        logger.info("calculateCurrencyConversion called with {} to {}, quantity {}", from, to, quantity);

        CurrencyConversion currencyConversion = currencyExchangeProxy.getExchangeRate(from, to);
        currencyConversion.setQuantity(quantity);
        currencyConversion.setTotalCalculatedAmount(quantity.multiply(currencyConversion.getConversionMultiple()));
        return currencyConversion;
    }
}
