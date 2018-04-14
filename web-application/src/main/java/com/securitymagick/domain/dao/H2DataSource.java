package com.securitymagick.domain.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class H2DataSource {

	@Bean
	public DataSource dataSource() {
		EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.addScript("db/sql/create-users-db.sql")
			.addScript("db/sql/create-posts-db.sql")
			.addScript("db/sql/create-comments-db.sql")
			.addScript("db/sql/create-logtable-db.sql")
			.addScript("db/sql/create-admin-db.sql")
    		.addScript("db/sql/insert-users.sql")
			.addScript("db/sql/insert-posts.sql")
			.addScript("db/sql/insert-comments.sql")
			.addScript("db/sql/insert-admin.sql")
    		.build();
		return db;
	}
}