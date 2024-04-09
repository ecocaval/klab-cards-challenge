package com.klab.cards.challenge.core.config.properties.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class CacheBaseProperties {

    private String cacheName;

    private long heapEntries;

    private long timeToLiveInSeconds;
}