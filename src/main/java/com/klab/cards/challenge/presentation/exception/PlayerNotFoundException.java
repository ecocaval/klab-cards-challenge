package com.klab.cards.challenge.presentation.exception;

import com.klab.cards.challenge.core.message.ErrorMessage;
import com.klab.cards.challenge.presentation.exception.base.NotFoundException;

import java.util.Map;

public class PlayerNotFoundException extends NotFoundException {

    public PlayerNotFoundException(String playerId) {
        super(ErrorMessage.ERROR_PLAYER_NOT_FOUND_BY_ID.getMessage(), Map.of("playerId", playerId));
    }
}
