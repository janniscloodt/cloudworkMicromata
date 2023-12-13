package com.oskarwiedeweg.cloudwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

//    @Bean
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .driverClassName("org.postgresql.Driver")
//                .build();
//    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
