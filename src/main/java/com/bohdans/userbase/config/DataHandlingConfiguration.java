package com.bohdans.userbase.config;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.List;

@Configuration
public class DataHandlingConfiguration {

    @Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager chainedTransactionManager(List<PlatformTransactionManager> dataSourceTransactionManagers){
        return new ChainedTransactionManager(dataSourceTransactionManagers.toArray(new PlatformTransactionManager[0]));
    }

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }
}
