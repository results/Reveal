package com.reveal.application;

import java.util.Optional;
import java.util.logging.Logger;

import com.reveal.api.APIRequestUtlity;
import com.reveal.api.request.impl.CampaignDailyRequest;
import com.reveal.api.request.impl.CreateSessionRequest;
import com.reveal.api.response.impl.CampaignDailyResponse;
import com.reveal.api.response.impl.CreateSessionResponse;
import com.reveal.application.config.AppProperties;

public class Application {
	
	private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
	
	public static void main(String[] args) {
		AppProperties.loadProperties();
		LOGGER.info("Application started. Requests will automatically be sent and conversion endpoint calculations output in console.");
		CreateSessionRequest createSessionRequest = new CreateSessionRequest();
		APIRequestUtlity.sendPOSTRequest(createSessionRequest);//submit and gets results 
		CreateSessionResponse createSessionResponse = createSessionRequest.getResponse().get();// or get results this way
		CampaignDailyRequest campaignDailyRequest = new CampaignDailyRequest();
		CampaignDailyResponse campaignDailyResponse = null;
		Optional<?> responseOptional = APIRequestUtlity.sendAPIRequest(campaignDailyRequest); //or get this way
		if(responseOptional.isPresent()) {
			campaignDailyResponse = (CampaignDailyResponse) responseOptional.get();
		} else {
			LOGGER.info("No results were retrieved, likely due to timeout (504). Rerun to get results.");
		}
		LOGGER.info("Exited.");
	}

}
