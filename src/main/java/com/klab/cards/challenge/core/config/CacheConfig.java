package com.klab.cards.challenge.core.config;

import com.klab.cards.challenge.core.config.properties.CardCacheProperties;
import com.klab.cards.challenge.core.config.properties.PlayersCacheProperties;
import com.klab.cards.challenge.core.config.properties.base.CacheBaseProperties;
import com.klab.cards.challenge.core.config.resolver.CardCacheResolver;
import com.klab.cards.challenge.core.config.resolver.PlayersCacheResolver;
import com.klab.cards.challenge.presentation.entity.Card;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.ehcache.config.CacheConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Caching;

import java.util.List;

import static java.time.Duration.ofSeconds;
import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.ExpiryPolicyBuilder.timeToLiveExpiration;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;
import static org.ehcache.jsr107.Eh107Configuration.fromEhcacheCacheConfiguration;

@Configuration
@EnableCaching
public class CacheConfig {

    final private CardCacheProperties cardCacheProperties;

    final private PlayersCacheProperties playersCacheProperties;

    @Autowired
    public CacheConfig(CardCacheProperties cardCacheProperties, PlayersCacheProperties playersCacheProperties) {
        this.cardCacheProperties = cardCacheProperties;
        this.playersCacheProperties = playersCacheProperties;
    }

    @Bean
    public org.springframework.cache.CacheManager cacheManager() {
        final javax.cache.CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();

        this.addPlayersCache(cacheManager);
        this.addCardCache(cacheManager);

        return new JCacheCacheManager(cacheManager);
    }


    private <K, V> void addCache(
            javax.cache.CacheManager cacheManager,
            CacheBaseProperties cacheProperties,
            Class<K> keyClass,
            Class<V> valueClass)
    {
        final CacheConfiguration<K, V> cacheConfiguration =
                newCacheConfigurationBuilder(keyClass, valueClass, heap(cacheProperties.getHeapEntries()))
                .withExpiry(timeToLiveExpiration(ofSeconds(cacheProperties.getTimeToLiveInSeconds())))
                .build();;

        cacheManager.destroyCache(cacheProperties.getCacheName());
        cacheManager.createCache(cacheProperties.getCacheName(), fromEhcacheCacheConfiguration(cacheConfiguration));
    }

    private void addPlayersCache(javax.cache.CacheManager cacheManager) {
        this.addCache(cacheManager, playersCacheProperties, SimpleKey.class, List.class);
    }

    @Bean("playersCacheResolver")
    public CacheResolver playersCacheResolver() {
        return new PlayersCacheResolver(cacheManager(), playersCacheProperties.getCacheName());
    }

    private void addCardCache(javax.cache.CacheManager cacheManager) {
        this.addCache(cacheManager, cardCacheProperties, String.class, Card.class);
    }

    @Bean("cardCacheResolver")
    public CacheResolver cardCacheResolver() {
        return new CardCacheResolver(cacheManager(), cardCacheProperties.getCacheName());
    }
}
