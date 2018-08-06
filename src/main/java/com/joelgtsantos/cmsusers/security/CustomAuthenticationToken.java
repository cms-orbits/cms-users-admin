/**
 * 
 */
package com.joelgtsantos.cmsusers.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Joel Santos
 *
 * cms-users-admin
 * 2018
 */
public class CustomAuthenticationToken implements Authentication {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authToken;


    public CustomAuthenticationToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    public String getToken() {
        return authToken;
    }
}
