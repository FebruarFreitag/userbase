package com.bohdans.userbase.config.postgres;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgresLiquibaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.postgresdb.liquibase")
    DataSourceProperties liquibasePostgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.postgresdb.liquibase.hikari")
    DataSource liquibasePostgresDataSource(@Qualifier("liquibasePostgresDataSourceProperties") DataSourceProperties liquibasePostgresDataSourceProperties) {
        return liquibasePostgresDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SpringLiquibase postgresLiquibase(@Qualifier("liquibasePostgresDataSource") DataSource liquibasePostgresDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/postgres-changelog.xml");
        liquibase.setDataSource(liquibasePostgresDataSource);
        return liquibase;
    }
}
