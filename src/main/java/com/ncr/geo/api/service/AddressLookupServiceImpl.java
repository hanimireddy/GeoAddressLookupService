/**
 * 
 */
package com.ncr.geo.api.service;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ncr.geo.api.domain.dto.GeoCodeResponse;
import com.ncr.geo.api.dto.AddressLookupResponse;
import com.ncr.geo.api.util.AppUtils;
import com.ncr.geo.api.util.AppContants;
import com.ncr.geo.api.util.AppInMemoryCacheManager;

/**
 * @author Hanimi
 *
 */
@Service
public class AddressLookupServiceImpl implements AddressLookupService{

	private static final Logger logger = LoggerFactory.getLogger(AddressLookupServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AppInMemoryCacheManager<String, AddressLookupResponse> inMemoryCacheManager;
	
	@Value("${geo.reversecoding.url}")
	private String geoReverseCodingUrl;
	
	public AddressLookupResponse getGeographicAddress(float latitude, float longitude)
	{
		logger.debug("AddressLookupServiceImpl:getGeographicAddress started.");
		AddressLookupResponse addrLookupResp = null;

	    try {
	    	Timestamp serviceReqTs = AppUtils.getCurrentDateTime();
	    	addrLookupResp = new AddressLookupResponse();
	    	String latlng = latitude+","+longitude;
	    	StringBuffer geoCodeUrl = new StringBuffer();
	    	geoCodeUrl.append(geoReverseCodingUrl);
	    	geoCodeUrl.append("?latlng=");
	    	geoCodeUrl.append(latlng);
	    	addrLookupResp.setServiceReqTs(serviceReqTs);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> entity = new HttpEntity<Object>(headers);

			ResponseEntity<GeoCodeResponse> respEntity = restTemplate.exchange(geoCodeUrl.toString(), HttpMethod.GET, entity, GeoCodeResponse.class);
			
			
			if(null != respEntity && respEntity.getStatusCode() == HttpStatus.OK)
			{
				GeoCodeResponse geoCodeResponse = respEntity.getBody();
				if(null != geoCodeResponse) 
				{
					 if(geoCodeResponse.getStatus().equals(AppContants.STATUS_OK)){
				    
						String formattedAddress = null;
						addrLookupResp.setStatusCode(HttpStatus.OK);
						addrLookupResp.setStatusDescription(geoCodeResponse.getStatus());
						if(!geoCodeResponse.getResults().isEmpty() && geoCodeResponse.getResults().size() > 0){
							formattedAddress = geoCodeResponse.getResults().get(0).getFormatted_address();
						}
						addrLookupResp.setFormattedAddress(formattedAddress);
						
						addrLookupResp.setLatitude(latitude);
						addrLookupResp.setLongitude(longitude);
						
						//update the cache object						
						inMemoryCacheManager.put(latlng, addrLookupResp);
					}else
					{
						addrLookupResp.setStatusCode(respEntity.getStatusCode());
						addrLookupResp.setErrorDescription(respEntity.getBody().getError_message());
					}
			}else{
				addrLookupResp.setStatusCode(respEntity.getStatusCode());
				addrLookupResp.setErrorDescription(AppContants.NULL_RESPONSE_FROM_GEOCODING_SERVICE);
				}
			}
			else{
				addrLookupResp.setStatusCode(respEntity.getStatusCode());
				addrLookupResp.setErrorDescription(AppContants.NULL_RESPONSE);
			}
			
		} catch (RestClientException e) {
			logger.error("AddressLookupServiceImpl:getGeographicAddress method. Error occured-{} ",e.getMessage());
			e.printStackTrace();
			throw e;
		}
	    logger.debug("AddressLookupServiceImpl:getGeographicAddress ended.");
	
		return addrLookupResp;
	}
}
