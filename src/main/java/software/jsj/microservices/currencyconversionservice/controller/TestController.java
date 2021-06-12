package software.jsj.microservices.currencyconversionservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import software.jsj.microservices.currencyconversionservice.bean.CurrencyConversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/inventory")
    public String getInventory() {
        //https://petstore.swagger.io/#/store/getInventory

        final ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(
                "https://petstore.swagger.io/v2/store/inventory", String.class);

        String response = responseEntity.getBody();
        log.info("Pets Inventory Response from Pet store: {}", response.toString());

        return response;
    }

    @GetMapping("/bystatus")
    public String findByStatus(@RequestParam(value = "status") String status) {
        //https://petstore.swagger.io/#/pet/findPetsByStatus

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("status", status);

        String url = "https://petstore.swagger.io/v2/pet/findByStatus";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url).queryParam("status", status);

        final ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(
                builder.build().encode().toUri(), String.class);

        log.info("*** Request Uri: {}", builder.build().encode().toUri().toString());
        String response = responseEntity.getBody();
        log.info("findByStatus Response from Pet store: {}", response.toString());

        return response;
    }


}
