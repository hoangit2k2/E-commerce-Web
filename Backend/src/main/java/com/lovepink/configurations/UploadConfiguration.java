package com.lovepink.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class UploadConfiguration implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

			registry.addResourceHandler("/uploads/**").addResourceLocations("/uploads/");
	}
	@Bean
	public MultipartResolver multipartresolver() {
		return new StandardServletMultipartResolver();
	}
	
	
	
}