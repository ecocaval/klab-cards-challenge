package com.klab.cards.challenge.presentation.exception;

import com.klab.cards.challenge.core.message.ErrorMessage;
import com.klab.cards.challenge.presentation.exception.base.BadRequestException;

import java.util.Map;

public class InvalidPlayerIdException extends BadRequestException {

    public InvalidPlayerIdException(String playerId) {
        super(ErrorMessage.ERROR_INVALID_GAME_ID.getMessage(), Map.of("playerId", playerId));
    }
}
