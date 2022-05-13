package com.reveal.models;

/**
 * Token received when creating session to applied to Authorization header.
 * Could be expanded in future to auto-renew when expired (time based if known)
 * @author John Fink
 *
 */
public class AuthorizationToken {

	private String token;
	
	public AuthorizationToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return token;
	}
	
	
}
