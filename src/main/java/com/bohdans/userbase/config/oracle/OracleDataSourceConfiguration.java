package com.bohdans.userbase.config.oracle;

import com.bohdans.userbase.entity.User;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.bohdans.userbase.repository.oracle",
        entityManagerFactoryRef = "oracleEntityManagerFactory",
        transactionManagerRef = "oracleTransactionManager"
)
public class OracleDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.oracledb.datasource")
    public DataSourceProperties oracleDatasourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name = "oracleDataSource")
    @Qualifier("oracleDataSource")
    @ConfigurationProperties("spring.oracledb.datasource.hikari")
    public DataSource oracleDataSource(@Qualifier("oracleDatasourceProperties")DataSourceProperties oracleDatasourceProperties) {
        return oracleDatasourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("oracleDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(User.class)
                .persistenceUnit("user")
                .build();
    }

    @Bean
    public PlatformTransactionManager oracleTransactionManager(
            @Qualifier("oracleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
