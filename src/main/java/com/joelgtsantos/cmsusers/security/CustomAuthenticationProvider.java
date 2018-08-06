/**
 * 
 */
package com.joelgtsantos.cmsusers.security;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Joel Santos
 *
 * cms-users-admin
 * 2018
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    UserInfoTokenServices userInfoTokenService;

    public CustomAuthenticationProvider(UserInfoTokenServices userInfoTokenService) {
        this.userInfoTokenService = userInfoTokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	Authentication auth = null;
    	
    	CustomAuthenticationToken googleAuthenticationToken = (CustomAuthenticationToken) authentication;
        try {
        	auth = userInfoTokenService.loadAuthentication(googleAuthenticationToken.getToken());
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (CustomAuthenticationToken.class.isAssignableFrom(authentication));
    }


}
