package com.cms.users.ws.inte;

import static com.jayway.restassured.RestAssured.when;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import com.cms.users.IntegrationTestCase;

public class ProfileImplIT extends IntegrationTestCase {
	private static final String RESOURCE = "/api/Profile/";

	@Before
	@Override
	public void setUp() {
		super.setUp();
	}

	
	@Test
	public void testGetAll() {
		when().get(RESOURCE).then().statusCode(HttpStatus.SC_OK);
	}

}
