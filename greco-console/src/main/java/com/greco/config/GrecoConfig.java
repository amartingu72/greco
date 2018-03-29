package com.greco.config;

import org.hibernate.type.descriptor.java.ImmutableMutabilityPlan;
import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.context.ServletContextAware;

import com.google.common.collect.ImmutableMap;
import com.sun.faces.config.ConfigureListener;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;

import java.util.EnumSet;
@SpringBootApplication
@ComponentScan({"com.greco"})
@EnableAutoConfiguration
@EntityScan("com.greco.entities")
@EnableJpaRepositories("com.greco.repositories")
public class GrecoConfig implements ServletContextAware{
	public static void main(String[] args) {
		SpringApplication.run(GrecoConfig.class, args);
	}
	/*
	 @Bean
	    public FilterRegistrationBean rewriteFilter() {
	        FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
	        rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
	                DispatcherType.ASYNC, DispatcherType.ERROR));
	        rwFilter.addUrlPatterns("/*");
	        return rwFilter;
	    }*/
	@Bean
	public static CustomScopeConfigurer viewScope() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		configurer.setScopes(
				new ImmutableMap.Builder<String, Object>().put("view", new ViewScope()).build());
		return configurer;
	}
	@Bean
	public ServletRegistrationBean facesServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(
				new FacesServlet(), "*.xhtml");
		registration.setLoadOnStartup(1);
		return registration;
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<ConfigureListener>(
				new ConfigureListener());
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
		servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
	}

	


}
