package com.app.playerservicejava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheEvictionService {
    @Autowired
    private CacheManager cacheManager;

    public void evictSpecificPlayer(String id) {
        Cache playerCache = cacheManager.getCache("playerById");
        if (playerCache != null) {
            playerCache.evict(id);
        }
    }

    public void clearAllCaches() {
        for (String cacheName : cacheManager.getCacheNames()) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        }
    }
}
