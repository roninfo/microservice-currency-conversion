package br.com.roninfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("br.com.roninfo.proxy")
@EnableDiscoveryClient
public class MicroserviceCurrencyConversionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCurrencyConversionApplication.class, args);
	}

}

