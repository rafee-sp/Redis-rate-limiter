package com.dev.rafee.ratelimiter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dev.rafee.ratelimiter.service.RateLimiterService;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private RateLimiterService rateLimiterService;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		  registry.addMapping("/**")
          .allowedOrigins("http://localhost:5173")
          .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
          .allowedHeaders("*");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new RateLimitInterceptor(rateLimiterService))
				.addPathPatterns("/api/**");  // all api routes
	}
	
}
