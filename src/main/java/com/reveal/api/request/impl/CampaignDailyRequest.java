package com.reveal.api.request.impl;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpStatus;

import com.reveal.api.request.APIRequest;
import com.reveal.api.request.EndpointConstants;
import com.reveal.api.response.impl.CampaignDailyResponse;
import com.reveal.utils.ConversionsCalculations;

public class CampaignDailyRequest extends APIRequest {
	
	private static final Logger LOGGER = Logger.getLogger(CampaignDailyRequest.class.getName());
	
	private static final EndpointConstants ENDPOINT = EndpointConstants.CONVERSION_DAILY;
	
	public CampaignDailyRequest() {
		super(ENDPOINT);
	}
	
	@Override
	public Optional<CampaignDailyResponse> getResponse() {
		return response;
	}
	
	@Override
	public void setHTTPVariables() {
		getURIVariableMap().put("campaign_id", "4522");
		//getHeaderMap().put("Authorization", "asdad");//not used in individual requests, set globally in requestutility
		//getParameterMap().put("segment", "Customers");//doesnt seem to be used for daily, but it works
	}

	@Override
	public void onRequest() {
		LOGGER.log(Level.INFO, "Submitted request for CampaignDailyRequest: "+getEndpointURI().toString());
	}

	@Override
	public void onResponseReceived(int status) {
		if(getResponse().isPresent()) {
			CampaignDailyResponse response = getResponse().get();
			if(response.getStatus() == HttpStatus.SC_OK) {
				LOGGER.log(Level.INFO, "Received DailyCampaign details. Summary below: \n"+ConversionsCalculations.displayAllCalculations(response.getDailyConversion()));	
			} else {
				LOGGER.log(Level.INFO, "Bad response received. Authorization token. status="+response.getStatus());	
			}
		} else {
			LOGGER.log(Level.INFO, "No response received from CampaignDailyRequest! status="+status);	
		}
	}
	
}
