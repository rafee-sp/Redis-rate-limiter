package com.dev.rafee.ratelimiter.service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

	private final RedisTemplate<String, String> redisTemplate;
	private static final int LIMIT = 30;	// REQUEST LIMIT
	private static final int TIME_WINDOW = 60;	// 1M
	
	public RateLimiterService(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	
	public boolean isRequestAllowed(String ipAddress) {
		
	    String key = "rate_limit:" + ipAddress;

	    Long count = redisTemplate.opsForValue().increment(key);

	    Long ttl = redisTemplate.getExpire(key);

	    if (ttl == -1) {
	        redisTemplate.expire(key, Duration.ofSeconds(TIME_WINDOW));
	    }

	    return count != null && count <= LIMIT;}
}
