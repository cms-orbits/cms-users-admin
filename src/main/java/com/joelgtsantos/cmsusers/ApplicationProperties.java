package com.cms.users;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationProperties {
	private String cookieSecret;
	
	private String cmsDomain;
	
	private String cmsPort;
	
	public String getCookieSecret() {
		return cookieSecret;
	}

	public void setCookieSecret(String cookieSecret) {
		this.cookieSecret = cookieSecret;
	}
	
	public String getCmsDomain() {
		return this.cmsDomain;
	}

	public void setCmsDomain(String cmsDomain) {
		this.cmsDomain = cmsDomain;
	}

	public String getCmsPort() {
		return cmsPort;
	}

	public void setCmsPort(String cmsPort) {
		this.cmsPort = cmsPort;
	}
}
