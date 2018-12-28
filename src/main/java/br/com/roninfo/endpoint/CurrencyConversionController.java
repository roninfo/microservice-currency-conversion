package br.com.roninfo.endpoint;

import br.com.roninfo.model.CurrencyConversionBean;
import br.com.roninfo.proxy.CurrencyExchangeServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("conversion")
public class CurrencyConversionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String testService() {
        return "Servico ok!";
    }

    @GetMapping(path = {"converter/from/{from}/to/{to}/quantity/{quantity}"})
    public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
                uriVariables);

        CurrencyConversionBean response = responseEntity.getBody();

        response.setTotalCalculatedAmount(quantity.multiply(response.getConversionMultiple()));

        return response;
    }

    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    @GetMapping(path = {"converter-feign/from/{from}/to/{to}/quantity/{quantity}"})
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

        logger.info("{}", response);

        response.setQuantity(quantity);
        response.setTotalCalculatedAmount(quantity.multiply(response.getConversionMultiple()));

        return response;
    }
}
