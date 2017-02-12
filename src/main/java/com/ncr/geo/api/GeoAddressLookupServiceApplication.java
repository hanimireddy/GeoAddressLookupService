package com.ncr.geo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.ncr.geo.api.dto.AddressLookupResponse;
import com.ncr.geo.api.util.AppInMemoryCacheManager;

@SpringBootApplication
public class GeoAddressLookupServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(GeoAddressLookupServiceApplication.class);
	
	public static void main(String[] args) {
		logger.info("GeoAddressLookupServiceApplication started.");
		SpringApplication.run(GeoAddressLookupServiceApplication.class, args);
	}
    
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(clientHttpRequestFactory());
	}
	
	private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(10000);
        factory.setConnectTimeout(10000);
        return factory;
    }
	
	@Bean
	public AppInMemoryCacheManager<String, AddressLookupResponse> inMemoryCacheManager(){
		long timeToLive = 200;
		long timeInterval = 500;
		int maxSize = 20;
		return new AppInMemoryCacheManager<String, AddressLookupResponse>(timeToLive,timeInterval,maxSize);
	}
}
