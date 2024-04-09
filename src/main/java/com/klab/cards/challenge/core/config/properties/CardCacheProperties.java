package com.klab.cards.challenge.core.config.properties;

import com.klab.cards.challenge.core.config.properties.base.CacheBaseProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConfigurationProperties(prefix = "ehcache.card-cache")
public class CardCacheProperties extends CacheBaseProperties {
}