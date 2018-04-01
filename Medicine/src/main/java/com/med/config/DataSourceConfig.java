package com.med.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

	// 数据源连接池的数据源
	@Bean
	public DataSource connectJDBCPoolDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver"); // 使用mysql驱动
		ds.setUrl("jdbc:mysql://localhost:3306/medicine?useUnicode=true&characterEncoding=utf8");
		ds.setUsername("root");
		ds.setPassword("123456");
		ds.setInitialSize(5); // 连接池启动时创建的连接数量
		return ds;
	}
	
/*	@Bean
	public DataSource connectC3P0PoolDataSource() 
			throws PropertyVetoException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setDriverClass("com.mysql.jdbc.Driver");
		ds.setJdbcUrl("jdbc:mysql://localhost:3306/medicine?useUnicode=true&characterEncoding=utf8");
		ds.setUser("root");
		ds.setPassword("123456");
	/*	ds.setInitialPoolSize(10);
		// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数.默认3
		ds.setAcquireIncrement(3);
		ds.setMaxStatements(1000);
		// 每60秒检查所有连接池中的空闲连接.默认0
		ds.setIdleConnectionTestPeriod(60);
		// 连接池中保持的最大连接数.默认15
		ds.setMaxPoolSize(20);
		// 连接池中保持的最小连接数
		ds.setMinPoolSize(5);
		// 最大空闲时间,1800秒内未使用则连接被丢弃，若为0则永不丢弃.默认0
		ds.setMaxIdleTime(1800);
		// 定义在从数据库获取新连接失败后重复尝试的次数.默认30
		ds.setAcquireRetryAttempts(30);
		// 两次连接中间隔时间，单位毫秒.默认1000
		ds.setAcquireRetryDelay(1000);
		// 连接关闭时默认将所有未提交的操作回滚.默认false
		ds.setAutoCommitOnClose(false);
		// 获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效  
		// 保留，并在下次调用getConnection()的时候继续尝试获取连接。
		// 如果设为true，那么在尝试获取连接失败后该数据源将申明已断开并永久关闭.默认false
		ds.setBreakAfterAcquireFailure(false);
		// 当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出  
		// SQLException,如设为0则无限期等待.单位毫秒.默认0
		ds.setCheckoutTimeout(120);
		// 因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的  
		// 时候都将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable  
		// 等方法来提升连接测试的性能
		ds.setTestConnectionOnCheckout(false);
		// 如果设为true那么在取得连接的同时将校验连接的有效性
		ds.setTestConnectionOnCheckin(true);
		
		return ds;
	}*/
}
