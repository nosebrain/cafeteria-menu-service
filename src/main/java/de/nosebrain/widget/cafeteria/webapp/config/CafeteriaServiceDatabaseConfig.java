package de.nosebrain.widget.cafeteria.webapp.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@org.springframework.context.annotation.Configuration
public class CafeteriaServiceDatabaseConfig {
  
  
  
  @Bean
  public static DataSource databaseConnection(@Value("${database.driver}") final String driverClassName,
      @Value("${database.url}") final String databaseUrl,
      @Value("${database.username}") final String databaseUserName,
      @Value("${database.password}") final String databasePassword) {
      final BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(driverClassName);
      dataSource.setUrl(databaseUrl);
      dataSource.setUsername(databaseUserName);
      dataSource.setPassword(databasePassword);
      
      dataSource.setValidationQuery("select 42");
      dataSource.setMinEvictableIdleTimeMillis(3600000);
      dataSource.setTimeBetweenEvictionRunsMillis(1800000);
      dataSource.setNumTestsPerEvictionRun(10);
      dataSource.setTestOnBorrow(true);
      dataSource.setTestOnReturn(true);
      dataSource.setTestWhileIdle(true);
      return dataSource;
  }
  
  @Bean
  public static SqlSessionFactory sqlSessionFactory(final DataSource databaseSource) throws IOException {
    final Resource configLocation = new ClassPathResource("de/nosebrain/widget/cafeteria/database/mysql/cafeteria-service-database.xml");
    final Reader reader = new InputStreamReader(configLocation.getInputStream());
    final XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader, null, null);
    final Configuration configuration = xmlConfigBuilder.parse();
    
    final TransactionFactory transactionFactory = new JdbcTransactionFactory();
    
    final Environment env = new Environment("production", transactionFactory, databaseSource);
    configuration.setEnvironment(env);
    
    final SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
    return builder.build(configuration);
  }
}
