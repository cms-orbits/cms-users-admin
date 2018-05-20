package com.cms.users;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Application {
	
	public static void main(String[] args) {
		Properties properties = new Properties();
        properties.setProperty("org.apache.tomcat.util.http. ServerCookie.STRICT_NAMING", "false");
        properties.setProperty("org.apache.tomcat.util.http.ServerCookie.ALLOW_HTTP_SEPARATORS_IN_V0", "true");

        new SpringApplicationBuilder(Application.class).properties(properties).run(args);
		//SpringApplication.run(Application.class, args);
	}

	boolean loadData = true;
}
