package com.bohdans.userbase.config.mysql;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MysqlLiquibaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.mysqldb.liquibase")
    DataSourceProperties liquibaseMysqlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.mysqldb.liquibase.hikari")
    DataSource liquibaseMysqlDataSource(@Qualifier("liquibaseMysqlDataSourceProperties") DataSourceProperties liquibaseMysqlDataSourceProperties) {
        return liquibaseMysqlDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SpringLiquibase mysqlLiquibase(@Qualifier("liquibaseMysqlDataSource") DataSource liquibaseMysqlDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/mysql-changelog.xml");
        liquibase.setDataSource(liquibaseMysqlDataSource);
        return liquibase;
    }
}
