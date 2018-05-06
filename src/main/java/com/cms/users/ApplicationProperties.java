package com.cms.users;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationProperties {
	private String cookieSecret;
	
	private String urlRedirect;
	
	public String getCookieSecret() {
		return cookieSecret;
	}

	public void setCookieSecret(String cookieSecret) {
		//this.b64CookieSecret = Base64.getEncoder().encodeToString(utf8Bytes(cookieSecret));
		this.cookieSecret = cookieSecret;
	}
	
	public String getUrlRedirect() {
		return this.urlRedirect;
	}

	public void setUrlRedirect(String urlRedirect) {
		//this.b64CookieSecret = Base64.getEncoder().encodeToString(utf8Bytes(cookieSecret));
		this.urlRedirect = urlRedirect;
	}
}
