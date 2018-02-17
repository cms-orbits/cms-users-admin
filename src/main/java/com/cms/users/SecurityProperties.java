package com.cms.users;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="security")
public class SecurityProperties {
	
	private String key;
	
	private String initVector;
	
    public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getInitVector() {
		return initVector;
	}
	public void setInitVector(String initVector) {
		this.initVector = initVector;
	}
}
