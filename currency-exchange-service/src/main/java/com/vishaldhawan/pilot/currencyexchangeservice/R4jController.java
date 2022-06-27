package com.vishaldhawan.pilot.currencyexchangeservice;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class R4jController {

    private final Logger logger = LoggerFactory.getLogger(R4jController.class);

    @GetMapping(path = "sample-api")
    @Retry(name = "sampleApiRetryConfig", fallbackMethod = "sampleApiFallbackMethod")
    // Circuit Breaker etc....
    public String sampleAPI() {
        logger.info("API Hit!!!");
        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080", String.class);
        return "Sample API";
    }

    public String sampleApiFallbackMethod(Exception ex) {
        return "Sample API Def";
    }
}
