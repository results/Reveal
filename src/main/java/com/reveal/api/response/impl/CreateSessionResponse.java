package com.reveal.api.response.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reveal.api.response.APIResponse;
import com.reveal.models.AuthorizationToken;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSessionResponse extends APIResponse {
		
	@JsonProperty("data")
	private AuthorizationToken authorizationToken;
	
	public AuthorizationToken getAuthorizationToken() {
		return authorizationToken;
	}

	public void setAuthorizationToken(AuthorizationToken token) {
		this.authorizationToken = token;
	}

	@Override
	public String toString() {
		return "CreateSessionResponse [token=" + authorizationToken + ", getStatus()=" + getStatus() + ", getRawResponse()="
				+ getRawResponse() + ", getMessages()=" + getMessages() + "]";
	}	
	
}
