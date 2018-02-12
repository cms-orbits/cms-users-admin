package com.cms.users;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Profile("test")
public class ApplicationTests {

	/**
	*
	*/
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	/*@Mock
	private SecurityContext securityContext;

	@Mock
	private OAuth2Authentication principal;

	@Mock
	private Authentication authentication;

	@Mock
	private User loggedUser;
	 */
	/**
	*
	*/
	@Test
	public void contextLoads() {
	}

}
