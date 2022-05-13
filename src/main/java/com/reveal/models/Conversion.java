package com.reveal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Conversion {
	
	@JsonProperty("dimensions")
	private ConversionDimensions conversionDimensions;
	
	private Metrics metrics;

	public ConversionDimensions getConversionDimensions() {
		return conversionDimensions;
	}

	public void setConversionDimensions(ConversionDimensions conversionDimensions) {
		this.conversionDimensions = conversionDimensions;
	}

	public Metrics getMetrics() {
		return metrics;
	}

	public void setMetrics(Metrics metrics) {
		this.metrics = metrics;
	}

}
