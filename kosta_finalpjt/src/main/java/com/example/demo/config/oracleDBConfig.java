package com.example.demo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.example.demo.oracledb",
    entityManagerFactoryRef = "oracleEntityManagerFactory",
    transactionManagerRef = "oracleTransactionManager"
)
public class oracleDBConfig {

  @Primary
  @Bean
  public PlatformTransactionManager oracleTransactionManager(){
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(oracleEntityManagerFactory().getObject());

    return transactionManager;
  }

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(){
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

    em.setDataSource(oracleDataSource());
    em.setPackagesToScan("com.example.demo.oracledb");
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setShowSql(true);
    vendorAdapter.setGenerateDdl(true);
    em.setJpaVendorAdapter(vendorAdapter);

    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.dialect","org.hibernate.dialect.OracleDialect");
    properties.put("hibernate.bm2ddl.auto","update");
    properties.put("hibernate.format_sql",true);

    em.setJpaPropertyMap(properties);

    return em;
  }

  @Primary
  @Bean(name = "oracleDataSource")
  public DataSource oracleDataSource(){
    return DataSourceBuilder.create()
        .driverClassName("oracle.jdbc.driver.OracleDriver")
        .url("jdbc:oracle:thin:@192.168.0.36:1521/xe")
        .username("hr")
        .password("hr")
        .build();
  }
}
