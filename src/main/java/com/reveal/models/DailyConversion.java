package com.reveal.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyConversion {
	
	private Range range;
	
	@JsonProperty("dimensions")
	private List<String> dimensionHeaders;
	
	@JsonProperty("metrics")
	private List<String> metricHeaders;
	
	@JsonProperty("rows")
	private List<Conversion> converions;

	public List<String> getDimensionHeaders() {
		return dimensionHeaders;
	}

	public void setDimensionHeaders(List<String> dimensionHeaders) {
		this.dimensionHeaders = dimensionHeaders;
	}

	public List<String> getMetricHeaders() {
		return metricHeaders;
	}

	public void setMetricHeaders(List<String> metricHeaders) {
		this.metricHeaders = metricHeaders;
	}

	public List<Conversion> getConverions() {
		return converions;
	}

	public void setConverions(List<Conversion> getConverions) {
		this.converions = getConverions;
	}

	public Range getRange() {
		return range;
	}

	public void setRange(Range range) {
		this.range = range;
	}
	
}
