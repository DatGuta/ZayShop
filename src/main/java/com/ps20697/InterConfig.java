package com.ps20697;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ps20697.Interceptor.SecurityInterceptor;





@Configuration
public class InterConfig implements WebMvcConfigurer{

	
	
	@Autowired
	SecurityInterceptor auth;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		
		
		registry.addInterceptor(auth)
			.addPathPatterns("/viewCart","/Admin")
			.excludePathPatterns("/Shop", "/About","/Contact","/shop/shop","/product");
		
	}
}