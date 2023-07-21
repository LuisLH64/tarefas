package com.test.api.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigDatabase {
	
	@Value("${spring.datasource.username}")
	private String usuario;
	
	@Value("${spring.datasource.password}")
	private String senha;
	
	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder
            .create()
            .url("jdbc:postgresql://localhost:5432/postgres")
            .username(usuario)
            .password(senha)
            .build();
	}
}
