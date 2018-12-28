package br.com.roninfo.proxy;

import br.com.roninfo.model.CurrencyConversionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Com o uso do Ribbon para realizar o loadbalance, retiramos a url fixa.
//@FeignClient(name="forex-service", url="localhost:8000")
@FeignClient(name="forex-service")
@RibbonClient(name="forex-service")
public interface CurrencyExchangeServiceProxy {

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    CurrencyConversionBean retrieveExchangeValue (@PathVariable String from, @PathVariable String to);
}
