package com.reveal.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reveal.models.constants.ConversionType;
import com.reveal.models.constants.DeviceType;

public class ConversionDimensions {
	
	@JsonProperty("Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
	private Date date;
	
	@JsonProperty("Device Type")
	private DeviceType deviceType;
	
	@JsonProperty("Conversion Type")
	private ConversionType conversionType;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public ConversionType getConversionType() {
		return conversionType;
	}

	public void setConversionType(ConversionType conversionType) {
		this.conversionType = conversionType;
	}

	@Override
	public String toString() {
		return "ConversionDimensions [date=" + date + ", deviceType=" + deviceType + ", conversionType="
				+ conversionType + "]";
	}
	
}
