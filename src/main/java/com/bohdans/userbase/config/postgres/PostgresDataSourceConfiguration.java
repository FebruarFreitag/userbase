package com.bohdans.userbase.config.postgres;

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
        basePackages = "com.bohdans.userbase.repository.postgres",
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager"
)
public class PostgresDataSourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.postgresdb.datasource")
    public DataSourceProperties postgresDatasourceProperties(){
        return new DataSourceProperties();
    }

    @Bean(name = "postgresDataSource")
    @Qualifier("postgresDataSource")
    @ConfigurationProperties("spring.postgresdb.datasource.hikari")
    public DataSource postgresDataSource(@Qualifier("postgresDatasourceProperties")DataSourceProperties postgresDatasourceProperties){
        return postgresDatasourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(User.class)
                .persistenceUnit("user")
                .build();
    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager(
            @Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
