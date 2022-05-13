package com.reveal.api.request;

import java.util.HashMap;

import com.reveal.api.response.APIResponse;
import com.reveal.api.response.impl.CampaignDailyResponse;
import com.reveal.api.response.impl.CreateSessionResponse;

/**
 * Builds relationship between API endpoint and response handler
 * @author John Fink
 *
 */
public enum EndpointConstants {
	
	//Variables can be placed in URL: e.g ${campaign_id} will be replaced by 4522 if in request URIVariablemap contains key "campaign_id"
	//HTTP method, URL, ResponseHandler.class
	CREATE_SESSION("POST", "https://gateway-service.revealmobile.com/session", CreateSessionResponse.class),
	CONVERSION_DAILY("GET", "https://gateway-service.revealmobile.com/report/campaign/${campaign_id}/conversion/daily", CampaignDailyResponse.class);
	
	
	private static HashMap<String, EndpointConstants> urlEndpointMap = new HashMap<>();//map to cache URl and our endpoint;
	
    static {
        for (EndpointConstants e : values()) {
        	urlEndpointMap.put(e.URL, e);
        }
    }
    private final String httpMethod;
	private final String URL;
	private final Class<? extends APIResponse> responseHandlerClass;


	/**
	 * Builds relationship between endpoint and response
	 * @param URL Endpoint
	 * @param responseHandlerClass Class to handle response
	 */
    private EndpointConstants(String HttpMethod, String URL, Class<? extends APIResponse> responseHandlerClass) {
    	this.httpMethod = HttpMethod;
        this.URL = URL;
        this.responseHandlerClass = responseHandlerClass;
    }
    
    public EndpointConstants valueOfURL(String URL) {
		return urlEndpointMap.get(URL);	
    }
    
    /**
     * Gets URL
     * @return String Endpoint URL
     */
	public String getURL() {
		return URL;
	}
    
	/**
	 * Gets ResponseHandler
	 * @return Class responseHandler class
	 */
	public Class<?> getResponseHandlerClass() {
		return responseHandlerClass;
	}

	public String getHttpMethod() {
		return httpMethod;
	}


	
}
