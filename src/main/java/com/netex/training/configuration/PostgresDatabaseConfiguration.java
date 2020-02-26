package com.netex.training.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(basePackages = "com.netex.training.repository.postgres",
        entityManagerFactoryRef = "postgresDatabaseEntityManager", transactionManagerRef = "postgresDatabaseTransactionManager")
public class PostgresDatabaseConfiguration {

    @Autowired
    private Environment env;

    public PostgresDatabaseConfiguration() {
        super();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean postgresDatabaseEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(postgresDataSource());
        em.setPackagesToScan("com.netex.training.model");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.persistent.jpa.properties.hibernate.dialect "));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource postgresDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.persistent.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.persistent.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.persistent.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.persistent.datasource.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager postgresDatabaseTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(postgresDatabaseEntityManager().getObject());
        return transactionManager;
    }
}
