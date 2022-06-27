package com.vishaldhawan.pilot.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url = "localhost:8000")
//@FeignClient(name = "currency-exchange") With Eureka Naming Server, Automatic Discovery of instances of currency-exchange service

//Kubernetes
@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")
public interface CurrencyExchangeProxy {

    @GetMapping(path = "currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion getExchangeRate(@PathVariable("from") String from, @PathVariable("to") String to);

}
