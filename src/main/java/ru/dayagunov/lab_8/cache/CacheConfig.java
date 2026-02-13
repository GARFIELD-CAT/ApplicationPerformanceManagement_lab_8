package ru.dayagunov.lab_8.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        Cache allTasksCache = new CaffeineCache("allTasks", Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .recordStats()
                .build());

        Cache taskCache = new CaffeineCache("task", Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .recordStats()
                .build());

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(List.of(allTasksCache, taskCache));
        return cacheManager;

    }
}