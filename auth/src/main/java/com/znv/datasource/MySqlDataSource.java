package com.znv.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * MySql数据库配置
 */
@Configuration
@MapperScan(basePackages = { "com.znv.dao.mapper" }, sqlSessionFactoryRef = "MySqlSessionFactory")
public class MySqlDataSource {
	/**
	 * MySql数据源
	 * 
	 * @return 返回JDBC POOL
	 */
	@Bean(name = "primaryDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.mysql")
	public javax.sql.DataSource dataSource() {
		return new org.apache.tomcat.jdbc.pool.DataSource();
	}

	/**
	 * 
	 * @return sqlSessionFactory
	 * @throws Exception
	 *             抛出异常
	 */
	@Bean(name = "MySqlSessionFactory")
	@Primary
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver
				.getResources("classpath:/mapper/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}

	/**
	 * 
	 * @return mysqlPlatformTransactionManager
	 */
	@Bean(name = "mysqlPlatformTransactionManager")
	@Primary
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}