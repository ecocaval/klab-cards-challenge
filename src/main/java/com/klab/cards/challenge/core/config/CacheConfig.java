package com.klab.cards.challenge.core.config;

import com.klab.cards.challenge.core.config.properties.CardCacheProperties;
import com.klab.cards.challenge.core.config.properties.CardsCacheProperties;
import com.klab.cards.challenge.core.config.properties.PlayerCacheProperties;
import com.klab.cards.challenge.core.config.properties.PlayersCacheProperties;
import com.klab.cards.challenge.core.config.properties.base.CacheBaseProperties;
import com.klab.cards.challenge.core.config.resolver.CardCacheResolver;
import com.klab.cards.challenge.core.config.resolver.CardsCacheResolver;
import com.klab.cards.challenge.core.config.resolver.PlayerCacheResolver;
import com.klab.cards.challenge.core.config.resolver.PlayersCacheResolver;
import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.entity.Player;
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

    final private CardsCacheProperties cardsCacheProperties;

    final private CardCacheProperties cardCacheProperties;

    final private PlayersCacheProperties playersCacheProperties;

    final private PlayerCacheProperties playerCacheProperties;

    @Autowired
    public CacheConfig(
            CardsCacheProperties cardsCacheProperties,
            CardCacheProperties cardCacheProperties,
            PlayersCacheProperties playersCacheProperties,
            PlayerCacheProperties playerCacheProperties
    ) {
        this.cardsCacheProperties = cardsCacheProperties;
        this.cardCacheProperties = cardCacheProperties;
        this.playersCacheProperties = playersCacheProperties;
        this.playerCacheProperties = playerCacheProperties;
    }

    @Bean
    public org.springframework.cache.CacheManager cacheManager() {
        final javax.cache.CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();

        this.addPlayersCache(cacheManager);
        this.addPlayerCache(cacheManager);
        this.addCardsCache(cacheManager);
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

    private void addPlayerCache(javax.cache.CacheManager cacheManager) {
        this.addCache(cacheManager, playerCacheProperties, String.class, Player.class);
    }

    @Bean("playerCacheResolver")
    public CacheResolver playerCacheResolver() {
        return new PlayerCacheResolver(cacheManager(), playerCacheProperties.getCacheName());
    }

    private void addCardsCache(javax.cache.CacheManager cacheManager) {
        this.addCache(cacheManager, cardsCacheProperties, SimpleKey.class, List.class);
    }

    @Bean("cardsCacheResolver")
    public CacheResolver cardsCacheResolver() {
        return new CardsCacheResolver(cacheManager(), cardsCacheProperties.getCacheName());
    }

    private void addCardCache(javax.cache.CacheManager cacheManager) {
        this.addCache(cacheManager, cardCacheProperties, String.class, Card.class);
    }

    @Bean("cardCacheResolver")
    public CacheResolver cardCacheResolver() {
        return new CardCacheResolver(cacheManager(), cardCacheProperties.getCacheName());
    }
}
