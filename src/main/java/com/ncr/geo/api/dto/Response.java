/**
 * 
 */
package com.ncr.geo.api.dto;

import org.springframework.http.HttpStatus;

/**
 * @author Hanimi
 *
 */
public class Response {
	private HttpStatus statusCode;
	private String statusDescription;
	private String errorDescription;
	
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
}
