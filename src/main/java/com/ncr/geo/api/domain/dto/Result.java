package com.ncr.geo.api.domain.dto;

import java.util.List;

public class Result {

	private List<AddressComponent> address_components = null;
	private String formatted_address;
	private Geometry geometry;
	private String placeId;
	private List<String> types = null;
	

	public List<AddressComponent> getAddress_components() {
		return address_components;
	}
	public void setAddress_components(List<AddressComponent> address_components) {
		this.address_components = address_components;
	}
	public String getFormatted_address() {
		return formatted_address;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	@Override
	public String toString() {
		return "Result [address_components=" + address_components + ", formatted_address=" + formatted_address
				+ ", geometry=" + geometry + ", placeId=" + placeId + ", types=" + types + "]";
	}
	

}
