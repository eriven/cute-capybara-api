package com.rizzler.capyapi.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Spring Boot auto-configures a ConcurrentMapCacheManager when no other
    // cache provider (Redis, Caffeine, etc.) is on the classpath.
    // No extra beans needed — @EnableCaching is sufficient for Phase 3.
}
