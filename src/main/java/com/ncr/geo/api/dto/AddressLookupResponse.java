/**
 * 
 */
package com.ncr.geo.api.dto;

import java.sql.Timestamp;


/**
 * @author Hanimi
 *
 */

public class AddressLookupResponse extends Response{

	private String formattedAddress;
	private float latitude;
	private float longitude;
	private Timestamp serviceReqTs;
	
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude2) {
		this.latitude = latitude2;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude2) {
		this.longitude = longitude2;
	}
	public Timestamp getServiceReqTs() {
		return serviceReqTs;
	}
	public void setServiceReqTs(Timestamp serviceReqTs) {
		this.serviceReqTs = serviceReqTs;
	}
	@Override
	public String toString() {
		return "AddressLookupResponse [formattedAddress=" + formattedAddress + ", latitude=" + latitude + ", longitude="
				+ longitude + ", serviceReqTs=" + serviceReqTs + "]";
	}
	
	
	
}
