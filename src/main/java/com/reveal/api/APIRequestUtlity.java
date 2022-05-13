package com.reveal.api;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reveal.api.request.APIRequest;
import com.reveal.api.request.EndpointConstants;
import com.reveal.api.response.APIResponse;
import com.reveal.models.AuthorizationToken;

/**
 * Utility class for handling simple post/get requests. Cannot be instantiated or overridden
 * @author John Fink
 *
 */
public final class APIRequestUtlity {
	
	private static final Logger LOGGER = Logger.getLogger(APIRequestUtlity.class.getName());
	
    private static final ObjectMapper mapper = new ObjectMapper();
    
	private static Map<String,Header> defaultHeaders = new HashMap<>();
    
    private static HttpClientBuilder clientBuilder = null;
    
    private static AuthorizationToken authToken = null;
    
    static {
		defaultHeaders.put("Accept", new BasicHeader("Accept", "application/json"));
    }
    
    //prevent instantiating
    private APIRequestUtlity() {
    	throw new UnsupportedOperationException();
	}       
    
    /**
     * Sets auth token for default headers
     * @param authToken
     */
	public static final void setAuthToken(AuthorizationToken authToken) {
		if(authToken != null) {
			APIRequestUtlity.authToken = authToken;
		    defaultHeaders.put("Authorization", new BasicHeader("Authorization", authToken.getToken()));
	    	configureClientBuilder();//recreate to update token
		}
	}
	
    /**
     * Create reusable configs. Call to re-create (Change API key in default header)
     */
	private static final void configureClientBuilder() {
		final int timeout = 5 * 60; // seconds (5 minutes)
		RequestConfig config = RequestConfig.custom()// occasionally times out, should fix if local error
				.setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000)
				.setSocketTimeout(timeout * 1000).build();
		clientBuilder = HttpClients.custom().setDefaultRequestConfig(config).setDefaultHeaders(defaultHeaders.values());
	}
            
    /**
     * Creates client.
     * @return CloseableHttpClient httpclient for requests
     */
    private static final CloseableHttpClient createHTTPClient() {
	    if(clientBuilder == null) {
	    	configureClientBuilder();
	    }
		return clientBuilder.build();	
    }
	
	/**
	 * Maps httpresponse to appropriate APIResponse class wrapped in an Optional
	 * @param endpoint
	 * @param httpResponse
	 * @return Optional<? extends APIResponse>
	 * @throws ParseException
	 * @throws IOException
	 */
	private static final Optional<?> getOptionalResponseFromHTTPResponse(EndpointConstants endpoint, HttpResponse httpResponse) throws ParseException, IOException {
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		int status = httpResponse.getStatusLine().getStatusCode();
		if (responseString == null || "".equals(responseString)) {
			LOGGER.log(Level.WARNING, status+": No response received in request at: URL="+endpoint.getURL());
			return Optional.empty();
		}
		JSONObject json = new JSONObject(responseString);
		json.put("status", status).put("rawResponse", responseString);
		return Optional.ofNullable(mapper.readValue(json.toString(), endpoint.getResponseHandlerClass()));
	}
    
	/**
	 * Submits an API request. Determines HTTP POST or GET based off endpoint.
	 * @param APIRequest request pojo
	 * @return Optional<? extends APIResponse> Matching response child of APIResponse
	 */
	public static final Optional<? extends APIResponse> sendAPIRequest(APIRequest request) {
		if(request != null && request.getEndpoint() != null) {
			switch(request.getEndpoint().getHttpMethod()) {
				case "POST":
					return APIRequestUtlity.sendPOSTRequest(request);
				case "GET":
					return APIRequestUtlity.sendGETRequest(request);
			}
		}
		return Optional.empty();
	}
   
	/**
	 * Submits a POST HTTP API request.
	 * @param APIRequest request pojo
	 * @return Optional<? extends APIResponse> Matching response child of APIResponse
	 */
	@SuppressWarnings("unchecked")//this is okay, compiler error only. Safe on runtime
	public static final Optional<? extends APIResponse> sendPOSTRequest(APIRequest request) {
		if (request != null) {
			request.finalizeRequest();
			if(request.getEndpointURI() != null && request.getEndpoint() != null) {
				try (CloseableHttpClient client = createHTTPClient()) {
					HttpPost postRequest = new HttpPost(request.getEndpointURI());
					if(request.getHeaderMap() != null && request.getHeaderMap().size() > 0) {
						for (Entry<String, String> e : request.getHeaderMap().entrySet()) {//append headers
							postRequest.setHeader(e.getKey(), e.getValue());
						}
					}
					if (request.getJsonBody() != null) {// has a body, attach
						postRequest.setEntity(request.getJsonBody());
					}
					try (CloseableHttpResponse httpResponse = client.execute(postRequest)) {
						Optional<? extends APIResponse> responseOptional = (Optional<? extends APIResponse>) getOptionalResponseFromHTTPResponse(request.getEndpoint(), httpResponse);
						request.setResponse(responseOptional,httpResponse.getStatusLine().getStatusCode());
						return responseOptional;
					}
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Error with POST request.", e);
					e.printStackTrace();
				}
			}
		}
		return Optional.empty();
	}
		
	/**
	 * Submits a GET HTTP API request. 
	 * @param APIRequest request pojo
	 * @return Optional<? extends APIResponse> Matching response child of APIResponse
	 */
	@SuppressWarnings("unchecked")//this is okay, compiler error only. Safe on runtime
	public static final Optional<? extends APIResponse> sendGETRequest(APIRequest request) {
		if (request != null) {
			request.finalizeRequest();
			if(request.getEndpointURI() != null && request.getEndpoint() != null) {
				try (CloseableHttpClient client = createHTTPClient()) {
					HttpGet getRequest = new HttpGet(request.getEndpointURI());
					if(request.getHeaderMap() != null && request.getHeaderMap().size() > 0) {//append headers
						for (Entry<String, String> e : request.getHeaderMap().entrySet()) {
							getRequest.setHeader(e.getKey(), e.getValue());
						}
					}
					try (CloseableHttpResponse httpResponse = client.execute(getRequest)) {
						Optional<? extends APIResponse> responseOptional = (Optional<? extends APIResponse>) getOptionalResponseFromHTTPResponse(request.getEndpoint(), httpResponse);
						request.setResponse(responseOptional,httpResponse.getStatusLine().getStatusCode());
						return responseOptional;
					}
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Error with GET request.", e);
					e.printStackTrace();
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Returns current authorization token
	 * @return AuthorizationToken 
	 */
	public static final AuthorizationToken getAuthToken() {
		return authToken;
	}
	
}
