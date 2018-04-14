package com.securitymagick.domain.dao;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySQLDataSource {

	@Bean
	public DataSource mySqlDataSource() {
		HikariDataSource bean = new HikariDataSource();			
            bean.setDriverClassName("com.mysql.jdbc.Driver");
            bean.setJdbcUrl("jdbc:mysql://localhost:3306/hh");
            bean.setUsername("root");
            bean.setPassword("new-password");			
            bean.setConnectionTestQuery("select 1");
            return bean;	
	}
}