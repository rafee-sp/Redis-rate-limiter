package com.dev.rafee.ratelimiter.config;

import org.springframework.web.servlet.HandlerInterceptor;

import com.dev.rafee.ratelimiter.service.RateLimiterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RateLimitInterceptor implements HandlerInterceptor{

	private final RateLimiterService rateLimiterService;
	
	public RateLimitInterceptor(RateLimiterService rateLimiterService) {
		this.rateLimiterService = rateLimiterService;
	}
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String ipAddress = request.getRemoteAddr();
		
		if(!rateLimiterService.isRequestAllowed(ipAddress)) {
			
			response.setStatus(429);
			response.getWriter().write("Too many requests. Try again later");
			return false;
		}
	
		return true;
	}
    
}
