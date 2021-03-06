/**
 * 
 */
package com.ncr.geo.api.domain.dto;

import java.util.List;

/**
 * @author Hanimi
 *
 */
public class AddressComponent {
	private String long_name;
	private String short_name;
	private List<String> types = null;
	
	public String getLong_name() {
		return long_name;
	}
	public void setLong_name(String long_name) {
		this.long_name = long_name;
	}
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	
	@Override
	public String toString() {
		return "AddressComponent [long_name=" + long_name + ", short_name=" + short_name + ", types=" + types + "]";
	}
	
	
}
