package com.klab.cards.challenge.core.config.resolver;

import lombok.NonNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

import java.util.Collection;

import static java.util.Collections.singletonList;

public class CardsCacheResolver implements CacheResolver {

    private final CacheManager cacheManager;
    private final String cacheName;

    public CardsCacheResolver(CacheManager cacheManager, String cacheName) {
        this.cacheManager = cacheManager;
        this.cacheName = cacheName;
    }

    @NonNull
    @Override
    public Collection<? extends Cache> resolveCaches(@NonNull CacheOperationInvocationContext<?> context) {
        return singletonList(cacheManager.getCache(cacheName));
    }
}
