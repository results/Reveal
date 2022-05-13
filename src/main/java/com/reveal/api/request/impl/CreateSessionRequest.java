package com.reveal.api.request.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;

import com.reveal.api.APIRequestUtlity;
import com.reveal.api.request.APIRequest;
import com.reveal.api.request.EndpointConstants;
import com.reveal.api.response.impl.CreateSessionResponse;
import com.reveal.application.config.AppProperties;
import com.reveal.utils.ConversionsCalculations;

/**
 * Request to create an api session token. Token will expire periodically, in which a new token will need to be generated.
 * @author John Fink
 *
 */
public class CreateSessionRequest extends APIRequest {
	
	private static final Logger LOGGER = Logger.getLogger(CreateSessionRequest.class.getName());
	
	private static final EndpointConstants ENDPOINT = EndpointConstants.CREATE_SESSION;
	
	@Override
	public Optional<CreateSessionResponse> getResponse() {
		return response;
	}

	public CreateSessionRequest() {
		super(ENDPOINT);
	}
	
	@Override
	public Object buildJSONBody() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email_address", AppProperties.getProperty("email_address"));
		login.put("password", AppProperties.getProperty("password"));
		LOGGER.log(Level.INFO, "Added login details to CreateSessionRequest body: "+login);
		return login;	
	}

	@Override
	public void onRequest() {
		LOGGER.log(Level.INFO, "Submitted request for CreateSessionRequest: "+getEndpointURI().toString());
	}

	@Override
	public void onResponseReceived(int status) {
		if(getResponse().isPresent()) {
			CreateSessionResponse response = getResponse().get();
			if(response.getStatus() >= HttpStatus.SC_OK) {
				if(response.getAuthorizationToken() != null && response.getAuthorizationToken().getToken() != null) {
					LOGGER.log(Level.INFO, "Received AuthorizationToken: "+response.getAuthorizationToken().getToken());
					APIRequestUtlity.setAuthToken(response.getAuthorizationToken());
				} else {
					LOGGER.log(Level.INFO, "Bad response received. Check login details. status="+response.getStatus());	
				}
			} else {
				LOGGER.log(Level.INFO, "Bad response received. Check login details. status="+status);	
			}
		}
	}


		

}
