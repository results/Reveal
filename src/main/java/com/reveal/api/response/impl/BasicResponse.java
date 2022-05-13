package com.reveal.api.response.impl;

import com.reveal.api.response.APIResponse;

public class BasicResponse extends APIResponse {
				
	private Object data;	

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

}
