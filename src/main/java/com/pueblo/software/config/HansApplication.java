package com.pueblo.software.config;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
@EntityScan(basePackages = { "com.pueblo.software.model" })
@EnableJpaRepositories(basePackages = { "com.pueblo.software.repository" })
@ComponentScan(basePackages = { "com.pueblo.software" })
//@EnableTransactionManagement
public class HansApplication {

	public static void main(String[] args) {
		SpringApplication.run(HansApplication.class, args);
	}

	/* @Override
	 * public void addCorsMappings(CorsRegistry registry) {
	 * 
	 * registry.addMapping("/rest/getFavouriteNodes")
	 * .allowedOrigins("http://localhost:8081")
	 * .allowedMethods("POST", "GET", "OPTIONS");
	 * registry.addMapping("/login")
	 * .allowedOrigins("http://localhost:8081")
	 * .allowedMethods("POST", "GET", "OPTIONS")
	 * .allowedHeaders("*")
	 * 
	 * .allowCredentials(true).maxAge(3600);
	 * 
	 * // Add more mappings...
	 * } */

	/* @Bean
	 * public LocalSessionFactoryBean getSessionFactory() {
	 * LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
	 * //factoryBean.setDataSource(dataSource());
	 * factoryBean.setPackagesToScan("com.pueblo.software.model");
	 * //factoryBean.setHibernateProperties(hibernateProperties());
	 * return factoryBean;
	 * }
	 * 
	 * @Bean
	 * 
	 * @Autowired
	 * public HibernateTransactionManager transactionManager(SessionFactory s) {
	 * HibernateTransactionManager txManager = new HibernateTransactionManager();
	 * txManager.setSessionFactory(s);
	 * return txManager;
	 * } */
}
