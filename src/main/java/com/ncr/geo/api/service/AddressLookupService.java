/**
 * 
 */
package com.ncr.geo.api.service;

import com.ncr.geo.api.dto.AddressLookupResponse;

/**
 * @author Hanimi
 *
 */
public interface AddressLookupService {
	
	public AddressLookupResponse getGeographicAddress(float latitude, float longitude);

}
