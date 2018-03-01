package com.cms.users;

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
