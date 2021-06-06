package software.jsj.microservices.currencyconversionservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import software.jsj.microservices.currencyconversionservice.bean.CurrencyConversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    private static Logger log = LoggerFactory.getLogger(CurrencyConversionController.class);

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from, @PathVariable String to,
            @PathVariable BigDecimal quantity) {

        //make REST API calls to exchange microservice
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        final ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();
        log.info("Response from currency-exchange: {}", currencyConversion.toString());

        currencyConversion.setQuantity(quantity);
        BigDecimal totalCalculatedAmount = quantity.multiply(currencyConversion.getConversionMultiple());
        currencyConversion.setTotalCalculatedAmount(totalCalculatedAmount);

        return currencyConversion;
    }
}
