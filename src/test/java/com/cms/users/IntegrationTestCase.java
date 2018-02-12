package com.cms.users;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Profile("integration")
@Transactional
public class IntegrationTestCase {
	/**
	*
	*/
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${local.server.port}")
	private int serverPort;

	/**
	 * Prepares the test class for execution of web tests. Builds a MockMvc
	 * instance. Call this method from the concrete JUnit test class in the
	 * <code>@Before</code> setup method.
	 */
	@Before
	public void setUp() {

		RestAssured.port = serverPort;

	}

}
