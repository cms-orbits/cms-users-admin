package com.joelgtsantos.cmsusers.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.hibernate.envers.strategy.ValidityAuditStrategy;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.joelgtsantos.cmsusers.entity.CookieGenerator;
import com.joelgtsantos.cmsusers.entity.EventPublisher;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "oneEntityManagerFactory", transactionManagerRef = "oneTransactionManager", basePackages = {
		"com.joelgtsantos.cmsusers" }, repositoryFactoryBeanClass = org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean.class)
public class Config {
	@Resource
	private Environment env;

	@Primary
	@Bean(name = "oneTransactionManager")
	PlatformTransactionManager oneTransactionManager() {
		return new JpaTransactionManager(oneEntityManagerFactory().getObject());
	}

	@Primary
	@Bean(name = "oneEntityManagerFactory")
	LocalContainerEntityManagerFactoryBean oneEntityManagerFactory() {

		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		// jpaVendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPersistenceUnitName("db-one");
		factoryBean.setDataSource(oneDataSource());
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		factoryBean.setPackagesToScan(Config.class.getPackage().getName()); 

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", env.getRequiredProperty("spring.db-one.datasource.hibernate.dialect"));
		jpaProperties.put("hibernate.format_sql",
				env.getRequiredProperty("spring.db-one.datasource.hibernate.format_sql"));
		jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("spring.db-one.datasource.hibernate.show_sql"));
		jpaProperties.put("hibernate.hbm2ddl.auto",
				env.getRequiredProperty("spring.db-one.datasource.hibernate.hbm2ddl.auto"));
		
		if (env.containsProperty("spring.db-one.datasource.hibernate.default_catalog"))
		jpaProperties.put("hibernate.default_catalog",
				env.getRequiredProperty("spring.db-one.datasource.hibernate.default_catalog"));

		if (env.containsProperty("spring.db-one.datasource.hibernate.default_schema"))
		jpaProperties.put("hibernate.default_schema",
				env.getRequiredProperty("spring.db-one.datasource.hibernate.default_schema"));
		
		jpaProperties.put("hibernate.query.substitutions", "false");
		jpaProperties.put("hibernate.enable_lazy_load_no_trans", "true");

		/* ENVERS */
		jpaProperties.put("hibernate.auditable",
				env.getRequiredProperty("spring.db-one.datasource.hibernate.auditable"));
		jpaProperties.put("org.hibernate.envers.storeDataAtDelete",
				env.getRequiredProperty("spring.db-one.datasource.hibernate.envers.storeDataAtDelete"));
		
		if (env.containsProperty("spring.db-one.datasource.hibernate.envers.default_schema"))
			jpaProperties.put("org.hibernate.envers.default_schema",
					env.getRequiredProperty("spring.db-one.datasource.hibernate.envers.default_schema"));
		
		
		jpaProperties.put("org.hibernate.envers.audit_strategy", ValidityAuditStrategy.class.getName());
		
		if (env.containsProperty("spring.db-one.datasource.hibernate.envers.default_catalog"))
			jpaProperties.put("org.hibernate.envers.default_catalog",
					env.getRequiredProperty("spring.db-one.datasource.hibernate.envers.default_catalog"));
		
		factoryBean.setJpaProperties(jpaProperties);
		factoryBean.afterPropertiesSet();
		return factoryBean;
	}

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.db-one.datasource")
	public DataSource oneDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
	    return new Jackson2JsonMessageConverter();
	}
		

	@Bean
	public TopicExchange senderTopicExchange() {
		return new TopicExchange("cmsExchange");
	}
	
	@Bean
	public EventPublisher eventPublisher(RabbitTemplate rabbitTemplate, TopicExchange senderTopicExchange) {
	    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());	 
		return new EventPublisher(rabbitTemplate, senderTopicExchange);
	}
	
	@Bean
    public CookieGenerator cookieGenerator() {
        return new CookieGenerator();
    }
	
	@Bean
	public EmbeddedServletContainerCustomizer customCookieProcessor() {
	    return container -> {
	        if (container instanceof TomcatEmbeddedServletContainerFactory) {
	            TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
	            tomcat.addContextCustomizers(context -> context.setCookieProcessor(new LegacyCookieProcessor()));
	        }
	    };
	}

}
