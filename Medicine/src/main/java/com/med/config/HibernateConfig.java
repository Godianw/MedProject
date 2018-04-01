package com.med.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.support.nativejdbc.Jdbc4NativeJdbcExtractor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	// 配置Hibernate事务管理器
	@Bean
	public HibernateTransactionManager transactionManager(
			SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
	
	// 获取Hibernate Session
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		// 设置数据源
		sfb.setDataSource(dataSource);					
		// 扫描的实体类的位置
		sfb.setPackagesToScan(new String[] { "com.med.entity" } );	
		/* 设置属性 */
		Properties props = new Properties();
		props.setProperty("dialect", 
				"org.hibernate.dialect.MySQLDialect");
		props.setProperty("show_sql", "true");
		props.setProperty("format_sql", "true");
	/*	props.setProperty("current_session_context_class",
				 "org.springframework.orm.hibernate5.SpringSessionContext");
		props.setProperty("enable_lazy_load_no_trans", "true");*/
	/*	props.setProperty("hibernate.connection.provider_class", 
				"org.hibernate.c3p0.internal.C3P0ConnectionProvider");
		props.setProperty("hibernate.connection.username", "root");
		props.setProperty("hibernate.connection.password", "123456");
		props.setProperty("hibernate.connection.driver_class", 
				"com.mysql.jdbc.Driver");
		props.setProperty("hibernate.connection.url", 
				"jdbc:mysql://localhost:3306/medicine?useUnicode=true&characterEncoding=utf8");
		*/
		sfb.setHibernateProperties(props);
		
		return sfb;
	}
	
	// 配置bean后置处理器
	// 他会在@repository注解的类上添加一个通知器，捕获相关异常并由spring重新抛出
	@Bean
	public BeanPostProcessor persistenceTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
