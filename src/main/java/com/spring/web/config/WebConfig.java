package com.spring.web.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by clj on 2017/5/22.
 * Description:
 */
@Configuration
@EnableWebMvc
//@EnableJpaRepositories(basePackages = {"com.spring.web.repository"})
@ComponentScan(basePackages = {"com.spring.web"})
public class WebConfig extends WebMvcConfigurerAdapter{

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new FastJsonHttpMessageConverter());
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws IOException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties properties = PropertiesLoaderUtils.loadAllProperties("mysql.properties");
        dataSource.setDriverClassName(properties.getProperty("database.driver"));
        dataSource.setUsername(properties.getProperty("database.user"));
        dataSource.setPassword(properties.getProperty("database.password"));
        dataSource.setUrl(properties.getProperty("database.url"));
        return new JdbcTemplate(dataSource);
    }
}
