package com.cms.users;

import javax.annotation.Resource;
import javax.sql.DataSource;

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
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPersistenceUnitName("db-one");
		factoryBean.setDataSource(oneDataSource());
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		factoryBean.setPackagesToScan(Config.class.getPackage().getName());
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
