package com.reveal.api.request;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.text.StringSubstitutor;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reveal.api.response.APIResponse;

/**
 * Parent APIRequest. Template for future requests.
 * @author John Fink
 *
 */
public abstract class APIRequest {
		
	private static final Logger LOGGER = Logger.getLogger(APIRequest.class.getName());
	
    private static final ObjectMapper mapper = new ObjectMapper();
	
	private EndpointConstants endpoint;
	
	private URI endpointURI;
	
	private Map<String, String> uriVariableMap = new HashMap<String, String>();
	
	private Map<String, String> parameterMap = new HashMap<String, String>();
	
	private Map<String, String> headerMap = new HashMap<String, String>();
	
	private StringEntity jsonBody;
	
	//optional<APIResponse>, no type to avoid issues with overriding. IS safe, as its enforced to be child of APIResponse
	@SuppressWarnings("rawtypes")
	protected Optional response = Optional.empty();
	
	/**
	 * Returns optional containing response. Response type specified in child.
	 * @return Optional containing Response class
	 */
	@SuppressWarnings("rawtypes")
	public abstract Optional getResponse();
	
	/**
	 * Builds JSON body based on return type. Parses to JSON.
	 * @return Object to parse to JSON
	 */
	public Object buildJSONBody() {
		return null;
	}
	
	/**
	 * Called directly before request sent, but after request is fully built. Useful for logging info
	 */
	public void onRequest() {
	}
	
	/**
	 * Set variables such as URL, Query, OR header. COuld be used for Auth token if not global
	 */
	public void setHTTPVariables() {
	}
	
	/**
	 * Called to build body and call onrequest
	 */
	public final void finalizeRequest() {
		if(getJsonBody() == null) {
			getHeaderMap().put("Content-type", "application/json");//json body, set content to json 
			this.setJsonBody(buildJSONBody());
		}
		setHTTPVariables();
		if(endpointURI == null) {
			this.setEndpointURI(this.buildURI());			
		}
		onRequest();
	}
	

	/**
	 * Called when response is received. Useful for logging, or things you don't want to manage after calling
	 * @param status HTTP status from response
	 */
	public void onResponseReceived(int status) {	
	}
	
		
	/**
	 * Enforce all inheriting classes have an endpoint assigned
	 * @param endpoint
	 */
	public APIRequest(EndpointConstants endpoint) {
		if (endpoint == null) {
			throw new NullPointerException("Endpoint is null in BasicRequest. Endpoint cannot be null, please call super(endpoint).");
		}
		this.endpoint = endpoint;
	}
	
	
	/**
	 * Builds URI from EndpointConstant endpoint and variables from uriVariable and parameters
	 * @return URI built URI from EndpointConstant
	 */
	public URI buildURI() {
		try {
			if (this.getEndpoint() != null) {
				String endpointURL = this.getEndpoint().getURL();
				if (this.getURIVariableMap() != null && this.getURIVariableMap().size() > 0) {
					StringSubstitutor varReplacer = new StringSubstitutor(this.getURIVariableMap());
					endpointURL = varReplacer.replace(endpointURL);// replace variables in URI (for path)
				}
				URIBuilder builder = new URIBuilder(endpointURL);
				if (this.getParameterMap() != null && this.getParameterMap().size() > 0) {// adds all parameters in map
					for (Entry<String, String> e : this.getParameterMap().entrySet()) {
						builder.addParameter(e.getKey(), e.getValue());
					}
				}
				return builder.build();
			}
		} catch (URISyntaxException e) {
			LOGGER.log(Level.WARNING, "Error parsing URI for endpoint: " + this.getEndpoint().getURL(), e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Sets Optional<APIResponse> from HTTP Response
	 * @param response
	 */
	public final void setResponse(Optional<? extends APIResponse> response, int status) {
		this.response = response;
		onResponseReceived(status);
	}
	
	/**
	 * Returns the URI for the endpoint. If URI is null, it will be attempted to be built.
	 * @return URI Endpoint URI
	 */
	public URI getEndpointURI() {
		return endpointURI;
	}

	public void setEndpointURI(URI endpointURI) {
		this.endpointURI = endpointURI;
	}

	public Map<String, String> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, String> parameterMap) {
		this.parameterMap = parameterMap;
	}

	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	public Map<String, String> getURIVariableMap() {
		return uriVariableMap;
	}

	public void setURIVariableMap(Map<String, String> uriVariableMap) {
		this.uriVariableMap = uriVariableMap;
	}

	public EndpointConstants getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(EndpointConstants endpoint) {
		this.endpoint = endpoint;
	}
	
	public StringEntity getJsonBody() {
		return jsonBody;
	}
	
	public void setJsonBody(Object toStringEntity) {
	    try {
	    	if(toStringEntity instanceof String) {
	    		this.jsonBody = new StringEntity((String) toStringEntity);
	    	} else {
	    		this.jsonBody = new StringEntity(mapper.writeValueAsString(toStringEntity));
	    	}
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			LOGGER.log(Level.SEVERE,"Error parsing object to StringEntity for jsonBody.", e);
			e.printStackTrace();
		}
	}
	
	protected static ObjectMapper getMapper() {
		return mapper;
	}

}
