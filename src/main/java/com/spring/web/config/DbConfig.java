package com.spring.web.config;

import com.spring.web.component.jpa.MyRepositoryFactoryBean;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by clj on 2017/7/3.
 * Description:
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.spring.web.repositories"},
    entityManagerFactoryRef = "entityManagerFactory",
    repositoryFactoryBeanClass = MyRepositoryFactoryBean.class,
    transactionManagerRef = "jpaTransactionManager")
public class DbConfig {

    public final static String DATASOURCE_CONFIG = "mysql.properties";
    public final static String HIBERNATE_CONFIG = "hibernate.properties";

    @Bean(name = "dataSource")
    public DataSource dataSource() throws IOException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = PropertiesLoaderUtils.loadAllProperties(DATASOURCE_CONFIG);
        dataSource.setDriverClassName(properties.getProperty("database.driver"));
        dataSource.setUsername(properties.getProperty("database.user"));
        dataSource.setPassword(properties.getProperty("database.password"));
        dataSource.setUrl(properties.getProperty("database.url"));
        return  dataSource;
    }

    @Bean(name = "entityManagerFactory")
    @Resource(name = "dataSource")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) throws IOException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean
                .setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean
                .setPackagesToScan("com.nd.esp.cloudaltas.business.behavior.**.model",
                        "com.nd.esp.cloudaltas.business.insight.**.model");

        org.springframework.core.io.Resource resource = new ClassPathResource(HIBERNATE_CONFIG);
        Properties properties = PropertiesLoaderUtils.loadProperties(resource);

        entityManagerFactoryBean.setJpaProperties(properties);

        entityManagerFactoryBean.setPersistenceUnitName("entityManagerFactory");

        return entityManagerFactoryBean;
    }

    @Bean
    @Resource(name = "entityManagerFactory")
    public PlatformTransactionManager jpaTransactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    @Bean
    @Resource(name = "dataSource")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) throws IOException {
        return new JdbcTemplate(dataSource);
    }
}
