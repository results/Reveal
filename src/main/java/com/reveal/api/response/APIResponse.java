package com.reveal.api.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reveal.models.Message;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class APIResponse {
	
	private int status;
	
	private String rawResponse;
		
	private List<Message> messages;
	
	private static final ObjectMapper mapper = new ObjectMapper();
				
    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRawResponse() {
		return rawResponse;
	}

	public void setRawResponse(String rawResponse) {
		this.rawResponse = rawResponse;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	protected static ObjectMapper getMapper() {
		return mapper;
	}


	@Override
	public String toString() {
		return "APIResponse [status=" + status + ", rawResponse=" + rawResponse + ",  messages="
				+ messages + "]";
	}
	
	

}
