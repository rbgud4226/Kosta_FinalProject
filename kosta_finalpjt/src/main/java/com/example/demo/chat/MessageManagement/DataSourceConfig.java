package com.example.demo.chat.MessageManagement;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {
	
    @Bean
    @Lazy
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("oracle.jdbc.driver.OracleDriver")
                .url("jdbc:oracle:thin:@localhost:1521/xe")
                .username("hr")
                .password("hr")
                .build();
    }
   
    @Bean
    @Lazy
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
