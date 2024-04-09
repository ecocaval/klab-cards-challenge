package com.klab.cards.challenge.core.config.utils;

import com.klab.cards.challenge.presentation.usecase.CardUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CardsCacheWarmUp {

    private final CardUseCase cardUseCase;

    @Autowired
    public CardsCacheWarmUp(CardUseCase cardUseCase) {
        this.cardUseCase = cardUseCase;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void warmUpCaches() {
        this.cardUseCase.findAll();
    }
}
