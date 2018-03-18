package com.med.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

	// 数据源连接池的数据源
	@Bean
	public DataSource connectPoolDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver"); // 使用mysql驱动
		ds.setUrl("jdbc:mysql://localhost:3306/medicine?useUnicode=true&characterEncoding=utf8");
		ds.setUsername("root");
		ds.setPassword("123456");
		ds.setInitialSize(5); // 连接池启动时创建的连接数量
		return ds;
	}
}
