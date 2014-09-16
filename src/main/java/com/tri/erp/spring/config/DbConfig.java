package com.tri.erp.spring.config;
 
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;


@PropertySource(value = "classpath:application.properties")
@EnableTransactionManagement()
@EnableJpaRepositories("com.tri.erp.spring.repo")
@Configuration
public class DbConfig {

	@Autowired
	Environment env;
	
	@Bean
	public BoneCPDataSource dataSource() {
		BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
		boneCPDataSource.setDriverClass(env.getRequiredProperty("db.driver"));
		boneCPDataSource.setJdbcUrl(env.getRequiredProperty("db.url"));
		boneCPDataSource.setUsername(env.getRequiredProperty("db.username"));
		boneCPDataSource.setPassword(env.getRequiredProperty("db.password"));
		boneCPDataSource.setIdleConnectionTestPeriodInMinutes(60);
		boneCPDataSource.setIdleMaxAgeInMinutes(420);
		boneCPDataSource.setMaxConnectionsPerPartition(30);
		boneCPDataSource.setMinConnectionsPerPartition(10);
		boneCPDataSource.setPartitionCount(3);
		boneCPDataSource.setAcquireIncrement(5);
		boneCPDataSource.setStatementsCacheSize(100);
		
		return boneCPDataSource; 
	}
	
	@Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
	
	
	@Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(BoneCPDataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);
		vendorAdapter.setDatabasePlatform(env.getRequiredProperty("hibernate.dialect"));
        vendorAdapter.setDatabase(Database.MYSQL);
		
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.tri.erp.spring.model");
        factory.setDataSource(dataSource);

        Properties properties = new Properties();
        properties.setProperty("hibernate.cache.use_second_level_cache", "true");
        properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        properties.setProperty("hibernate.cache.use_query_cache", "true");
        properties.setProperty("hibernate.generate_statistics", "true");
        
        factory.setJpaProperties(properties);
        
        factory.afterPropertiesSet();
 
        return factory.getObject(); 
	}
	
	@Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
        JpaDialect jpaDialect = new HibernateJpaDialect();
        txManager.setEntityManagerFactory(entityManagerFactory);
        txManager.setJpaDialect(jpaDialect);
        
        return txManager; 
	 }
}
