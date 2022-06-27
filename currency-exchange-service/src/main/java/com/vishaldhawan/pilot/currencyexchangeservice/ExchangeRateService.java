package com.vishaldhawan.pilot.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public ExchangeRate getExchangeRate(String from, String to){
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateRepository.findByFromAndTo(from, to);

        if(!exchangeRateOptional.isPresent()) {
            throw new IllegalArgumentException("NOT FOUND");
        }

        return exchangeRateOptional.get();
    }
}
