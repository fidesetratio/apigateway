package com.application.security;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

public class CustomRemoteTokenServices {
	
	private String clientId;
	private String clientSecret;
	private String checkTokenEndPoint;
	
	private RestTemplate restTemplate;

	public CustomRemoteTokenServices(RestTemplate restOperations) {
		this.restTemplate = restOperations;
	}
	

	public Authentication loadAuthentication(String accessToken)
			 {
		// TODO Auto-generated method stub
		
		System.out.println("hohehoeoeeoda");
		
		HttpHeaders headers = new HttpHeaders();
		if (headers.getContentType() == null) {
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		}
		
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
		formData.add("token", accessToken);
		formData.add(clientId, clientSecret);
		Map map = restTemplate.exchange(this.checkTokenEndPoint, HttpMethod.POST,
				new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
		
		
		System.out.println(map);
		
		
		
		//String pathUrl = this.checkTokenEndPoint+"?token="+accessToken;
		
		
		
		return null;
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getCheckTokenEndPoint() {
		return checkTokenEndPoint;
	}

	public void setCheckTokenEndPoint(String checkTokenEndPoint) {
		this.checkTokenEndPoint = checkTokenEndPoint;
	}

}
