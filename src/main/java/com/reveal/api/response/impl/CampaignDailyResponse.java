package com.reveal.api.response.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reveal.api.response.APIResponse;
import com.reveal.models.DailyConversion;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class CampaignDailyResponse extends APIResponse {
		
	@JsonProperty("data")
	private DailyConversion dailyConversion;

	public DailyConversion getDailyConversion() {
		return dailyConversion;
	}

	public void setDailyConversion(DailyConversion dailyConversion) {
		this.dailyConversion = dailyConversion;
	}

	@Override
	public String toString() {
		return "CampaignDailyResponse [dailyConversion=" + dailyConversion + ", getStatus()=" + getStatus() + ", getMessages()="
				+ getMessages() + "]";
	}

	
	
}
