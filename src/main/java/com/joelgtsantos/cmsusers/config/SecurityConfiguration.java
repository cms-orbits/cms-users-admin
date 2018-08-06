/**
 * 
 */
package com.joelgtsantos.cmsusers.config;

import java.util.Arrays;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Joel Santos
 *
 *         cms-users-admin 2018
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.parentAuthenticationManager(authenticationManager());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().antMatcher("/**").authorizeRequests().antMatchers("/").permitAll().anyRequest()
				.authenticated().and().exceptionHandling().and()
				.addFilterBefore(googleTokenVerificationFilter(), BasicAuthenticationFilter.class);
	}

	@Bean
	public Filter googleTokenVerificationFilter() {
		CustomAuthenticationFilter googleAuthenticationFilter = new CustomAuthenticationFilter();
		return googleAuthenticationFilter;

	}

	@Bean
	public FilterRegistrationBean corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(-200);
		return bean;
	}

	
	@Bean
	public AuthenticationManager authenticationManager() {
	    return new ProviderManager(Arrays.asList(googleAuthenticationProvider(), githubAuthenticationProvider()));
	}
	
	@Bean
	public AuthenticationProvider googleAuthenticationProvider() {
		OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(),
				google().getClientId());
		tokenServices.setRestTemplate(googleTemplate);
		CustomAuthenticationProvider googleAuthenticationProvider = new CustomAuthenticationProvider(tokenServices);
		return googleAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationProvider githubAuthenticationProvider() {
		OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(),
				github().getClientId());
		tokenServices.setRestTemplate(googleTemplate);
		CustomAuthenticationProvider githubAuthenticationProvider = new CustomAuthenticationProvider(tokenServices);
		return githubAuthenticationProvider;
	}

	@Bean
	@ConfigurationProperties("security.google.client")
	public AuthorizationCodeResourceDetails google() {
		return new AuthorizationCodeResourceDetails();
	}
	

	@Bean
	@ConfigurationProperties("security.google.resource")
	public ResourceServerProperties googleResource() {
		return new ResourceServerProperties();
	}
	
	@Bean
	@ConfigurationProperties("security.github.client")
	public AuthorizationCodeResourceDetails github() {
		return new AuthorizationCodeResourceDetails();
	}
	

	@Bean
	@ConfigurationProperties("security.github.resource")
	public ResourceServerProperties githubResource() {
		return new ResourceServerProperties();
	}
}