package com.reveal.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metrics {
	
	@JsonProperty("Conversion Count")
	private int conversionCount;
	
	@JsonProperty("Conversion Percent")
	private double ConversionPercent;

	public int getConversionCount() {
		return conversionCount;
	}

	public void setConversionCount(int conversionCount) {
		this.conversionCount = conversionCount;
	}

	public double getConversionPercent() {
		return ConversionPercent;
	}

	public void setConversionPercent(double conversionPercent) {
		ConversionPercent = conversionPercent;
	}

	@Override
	public String toString() {
		return "Metrics [conversionCount=" + conversionCount + ", ConversionPercent=" + ConversionPercent + "]";
	}
	
}
