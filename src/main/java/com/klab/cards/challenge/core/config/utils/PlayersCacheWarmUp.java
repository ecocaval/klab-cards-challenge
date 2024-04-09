package com.klab.cards.challenge.core.config.utils;

import com.klab.cards.challenge.presentation.usecase.PlayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PlayersCacheWarmUp {

    private final PlayerUseCase playerUseCase;

    @Autowired
    public PlayersCacheWarmUp(PlayerUseCase playerUseCase) {
        this.playerUseCase = playerUseCase;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void warmUpCaches() {
        playerUseCase.findAll();
    }
}
