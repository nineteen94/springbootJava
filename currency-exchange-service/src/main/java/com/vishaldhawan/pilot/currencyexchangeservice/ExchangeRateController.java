package com.vishaldhawan.pilot.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {

    @Autowired
    private Environment environment;

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(path = "currency-exchange/from/{from}/to/{to}")
    public ExchangeRate getExchangeRate(@PathVariable("from") String from, @PathVariable("to") String to){
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(from, to);
        String port = environment.getProperty("server.port");

//        Change Kubernetes
        String host = environment.getProperty("HOSTNAME");
        String version = "v11";

        exchangeRate.setEnvironment(port + " " + host + " " + version);

        return exchangeRate;
    }
}
