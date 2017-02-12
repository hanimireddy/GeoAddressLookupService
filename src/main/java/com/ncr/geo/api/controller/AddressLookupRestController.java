/**
 * 
 */
package com.ncr.geo.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncr.geo.api.dto.AddressLookupRequest;
import com.ncr.geo.api.dto.AddressLookupResponse;
import com.ncr.geo.api.dto.CacheResultsResponse;
import com.ncr.geo.api.service.AddressLookupService;
import com.ncr.geo.api.util.AppInMemoryCacheManager;

/**
 * @author Hanimi
 *
 */

@RestController
@RequestMapping("/ncr/api")
public class AddressLookupRestController {

	private static final Logger logger = LoggerFactory.getLogger(AddressLookupRestController.class);

	@Autowired
	private AddressLookupService addressLookupService;

	@Autowired
	private AppInMemoryCacheManager<String, AddressLookupResponse> inMemoryCacheManager;

	@RequestMapping(value = "/retrieveGeographicAddress", method = RequestMethod.POST)
	public AddressLookupResponse retrieveGeographicAddress(@RequestBody AddressLookupRequest requestParms) {

		logger.debug("AddressLookupRestController:retrieveGeographicAddress method started!");
		AddressLookupResponse response = null;
		boolean isValidRequest = isValidCoordinates(requestParms);
		if (isValidRequest) {
			String latlng = requestParms.getLatitude() + "," + requestParms.getLongitude();
			response = inMemoryCacheManager.get(latlng);
			if (null == response)
				response = addressLookupService.getGeographicAddress(requestParms.getLatitude(),
						requestParms.getLongitude());
		}
		logger.debug("AddressLookupRestController:retrieveGeographicAddress method ended!");
		return response;

	}

	@RequestMapping(value = "/retrieveCachedDataStore", method = RequestMethod.GET)
	public CacheResultsResponse retrieveCachedDataStore(@RequestParam int cnt) {

		logger.info(
				"AddressLookupRestController.retrieveCachedDataStore method started: number of elements requested from cached Object {}",
				cnt);
		List<AddressLookupResponse> latestLookupReqsList = null;
		CacheResultsResponse cacheResultsResponse = null;
	
		try {
			cacheResultsResponse = new CacheResultsResponse();
			List<AddressLookupResponse> cachedObjList = inMemoryCacheManager.getCachedMapObjectList();
			// sort cachedObject list in ascending order
			cachedObjList.sort((a, b) -> b.getServiceReqTs().compareTo(b.getServiceReqTs()));

			int cachedObjSize = cachedObjList.size();
			logger.info("retrieveCachedDataStore method: total number of elements in the cached Object list {}",
					cachedObjSize);
			// to return the last n number of requests from n+1 list.
			if (!cachedObjList.isEmpty()) {
				if (cachedObjSize <= cnt)
					cacheResultsResponse.setLookupResp(cachedObjList);
				else {
					latestLookupReqsList = cachedObjList.stream().limit(cnt).collect(Collectors.toList());
					cacheResultsResponse.setLookupResp(latestLookupReqsList);
				}
			}else{
				cacheResultsResponse.setStatusDescription("No Results found!");
			}
			
			cacheResultsResponse.setStatusCode(HttpStatus.OK);
			
			
		} catch (Exception e) {
			cacheResultsResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
			cacheResultsResponse.setStatusDescription("Exception occured while retrieveing results from Cache Object");
			cacheResultsResponse.setErrorDescription(e.getMessage());
			logger.error(
					"AddressLookupRestController.retrieveCachedDataStore method : Exception {} occured while retrieveing results from Cache Object",
					e.getMessage());
			e.printStackTrace();
		}
		
		return cacheResultsResponse;

	}

	/**
	 * @param latitude
	 *            a latitude coordinate in decimal notation
	 * @param longitude
	 *            a longitude coordinate in decimal notation
	 */
	private boolean isValidCoordinates(AddressLookupRequest requestParms) {

		if (null != requestParms) {

			float latitude = requestParms.getLatitude();
			float longitude = requestParms.getLongitude();

			if (longitude > 180 || longitude < -180)
				throw new IllegalStateException("The longitude of a point must be between -180 and 180");

			if (latitude > 90 || latitude < -90)
				throw new IllegalStateException("The latitude of a point must be between -90 and 90");
		} else {
			throw new IllegalStateException("Longitude and Latitude values cannot be null");
		}

		return true;
	}

	@ExceptionHandler(IllegalArgumentException.class)
	void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), "Please try again and with a valid longitude and latitude.");
	}
}
