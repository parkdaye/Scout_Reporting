package com.aceproject.demo.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@PropertySource("classpath:application.properties")
public class DataConfig {
	
//	@Autowired
//	private ApplicationContext applicationContext;
//	
//	@Bean
//	@ConfigurationProperties(prefix = "spring.datasource.hikari")
//	public HikariConfig hikariConfig() {
//		return new HikariConfig();
//	}
//
//	@Bean
//	public DataSource dataSource() {
//		DataSource dataSource = new HikariDataSource(hikariConfig());
//		return dataSource;
//	}
//
//	@Bean
//	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//		sqlSessionFactoryBean.setDataSource(dataSource);
//		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/*Mapper.xml"));
//		return sqlSessionFactoryBean.getObject();
//	}
//
//	@Bean
//	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//		return new SqlSessionTemplate(sqlSessionFactory);
//	}
	
	
	@Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        // mybatis 설정
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        configuration.setCallSettersOnNulls(true);
        
        List<TypeHandler<?>> typeHandlers = new ArrayList<>();
        typeHandlers.add(new JodaDateTimeTypeHandler());

        String mapperLoc = "classpath*:/mapper/**/*.xml";
        Resource[] locations = new PathMatchingResourcePatternResolver().getResources(mapperLoc);

        // lazyConnection 설정
        // dataSource = new LazyConnectionDataSourceProxy(dataSource);

        // sqlSessionFactory 생성
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setTypeHandlers(typeHandlers.toArray(new TypeHandler<?>[typeHandlers.size()]));
        factory.setDataSource(dataSource);
        factory.setConfiguration(configuration);
        factory.setTypeAliasesPackage("com.aceproject.demo.model");
        factory.setMapperLocations(locations);

        return factory.getObject();
    }
	
}
