package com.ds.microservices.sport.tabletennis.config;

import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@ComponentScan("com.ds.microservices.sport.tabletennis")
@EntityScan("com.ds.microservices.sport.tabletennis.model")
@EnableJpaRepositories(basePackages = {"com.ds.microservices.sport.tabletennis.model", "com.ds.microservices.sport.tabletennis.repository"})
@PropertySource("classpath:db-config.properties")
public class DataConfiguration {

	protected Logger logger = Logger.getLogger(DataConfiguration.class.getName());
	
	@Autowired
	public DataSource dataSource;
	
}
