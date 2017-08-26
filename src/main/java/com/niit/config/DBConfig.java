package com.niit.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.niit.model.Blog;
import com.niit.model.Chat;
import com.niit.model.Comment;
import com.niit.model.Forum;
import com.niit.model.Friend;
import com.niit.model.Job;
import com.niit.model.JobApplication;
import com.niit.model.User;

@Configuration
@ComponentScan("com.niit")
@EnableTransactionManagement
public class DBConfig {
		
		
		
		@Bean(name="dataSource")
		public DataSource getOracleDataSource()
		{
			
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
			dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
			dataSource.setUsername("akshat");
			dataSource.setPassword("akshat");
			
			
			Properties connectionProperties = new Properties();

			connectionProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");

			dataSource.setConnectionProperties(connectionProperties);
			
			
			return dataSource;
		}
		
		
		@Autowired
		@Bean(name = "sessionFactory")
		public SessionFactory getSessionFactory(DataSource dataSource) {

			LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
			Properties connectionProperties = new Properties();

			connectionProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");

			sessionBuilder.addProperties(connectionProperties);
			sessionBuilder.addAnnotatedClass(User.class);
			sessionBuilder.addAnnotatedClass(Blog.class);
			sessionBuilder.addAnnotatedClass(Friend.class);
			sessionBuilder.addAnnotatedClass(Forum.class);
			sessionBuilder.addAnnotatedClass(Comment.class);
			sessionBuilder.addAnnotatedClass(Job.class);
			sessionBuilder.addAnnotatedClass(JobApplication.class);
			sessionBuilder.addAnnotatedClass(Chat.class);
			
			return sessionBuilder.buildSessionFactory();
		}

		@Autowired
		@Bean(name = "transactionManager")
		public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {

			HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

			return transactionManager;
		}

		/*@Bean
		public CommonsMultipartResolver multipartResolver() {
		    CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		    resolver.setDefaultEncoding("utf-8");
		    resolver.setMaxUploadSize(10000);
		    return resolver;
		}
		*/
		
		@Bean(name = "multipartResolver")
		public CommonsMultipartResolver getCommonsMultipartResolver() {
		    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		    multipartResolver.setMaxUploadSize(20971520);   // 20MB
		    multipartResolver.setMaxInMemorySize(1048576);  // 1MB
		    return multipartResolver;
		}
		

}

