package com.bohdans.userbase.config.oracle;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class OracleLiquibaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.oracledb.liquibase")
    DataSourceProperties liquibaseOracleDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.oracledb.liquibase.hikari")
    DataSource liquibaseOracleDataSource(@Qualifier("liquibaseOracleDataSourceProperties") DataSourceProperties liquibaseOracleDataSourceProperties) {
        return liquibaseOracleDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SpringLiquibase oracleLiquibase(@Qualifier("liquibaseOracleDataSource") DataSource liquibaseOracleDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/oracle-changelog.xml");
        liquibase.setDataSource(liquibaseOracleDataSource);
        return liquibase;
    }
}
