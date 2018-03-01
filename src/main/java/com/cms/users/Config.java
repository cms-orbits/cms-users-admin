package com.cms.users;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.envers.strategy.ValidityAuditStrategy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "oneEntityManagerFactory", transactionManagerRef = "oneTransactionManager", basePackages = {
		"com.cms.users" }, repositoryFactoryBeanClass = org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean.class)
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
		// ".entity");
		//factoryBean.setPackagesToScan("com.corpo.mos");

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
		
		//jpaProperties.put("hibernate.ejb.naming_strategy",env.getRequiredProperty("spring.db-one.datasource.hibernate.ejb.naming_strategy"));
		/*
		jpaProperties.put("hibernate.ejb.event.post-insert",env.getRequiredProperty("spring.db-one.datasource.hibernate.ejb.event.post-insert"));
		jpaProperties.put("hibernate.ejb.event.post-update",env.getRequiredProperty("spring.db-one.datasource.hibernate.ejb.event.post-update"));
		jpaProperties.put("hibernate.ejb.event.post-delete",env.getRequiredProperty("spring.db-one.datasource.hibernate.ejb.event.post-delete"));
		
		jpaProperties.put("hibernate.ejb.event.pre-collection-update",env.getRequiredProperty("spring.db-one.datasource.hibernate.ejb.event.pre-collection-update"));
		jpaProperties.put("hibernate.ejb.event.pre-collection-remove",env.getRequiredProperty("spring.db-one.datasource.hibernate.ejb.event.pre-collection-remove"));
		jpaProperties.put("hibernate.ejb.event.pre-collection-recreate",env.getRequiredProperty("spring.db-one.datasource.hibernate.ejb.event.pre-collection-recreate"));
		 */
		
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
}
