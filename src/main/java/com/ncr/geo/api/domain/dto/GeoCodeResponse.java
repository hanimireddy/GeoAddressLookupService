package com.ncr.geo.api.domain.dto;

import java.util.List;

public class GeoCodeResponse {

	private List<Result> results = null;
	private String error_message;
	private String status;

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	@Override
	public String toString() {
		return "GeoCodeResponse [results=" + results + ", error_message=" + error_message + ", status=" + status + "]";
	}

	
}
