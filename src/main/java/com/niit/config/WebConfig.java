package com.niit.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.niit")
//@ComponentScan(basePackages = { "org.example.springproject" }, excludeFilters = { @Filter( value = Configuration.class) })
public class WebConfig extends WebMvcConfigurerAdapter    {
	
	
	/*public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		 registry.addResourceHandler("/resources/**")
				 .addResourceLocations("/WEB-INF/resources/");
	}*/
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry  registry)
	{
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
    
}

