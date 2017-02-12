package com.ncr.geo.api.dto;

import java.util.List;

public class CacheResultsResponse extends Response{

	private List<AddressLookupResponse> lookupResp;

	public List<AddressLookupResponse> getLookupResp() {
		return lookupResp;
	}

	public void setLookupResp(List<AddressLookupResponse> lookupResp) {
		this.lookupResp = lookupResp;
	}
	
	
}
