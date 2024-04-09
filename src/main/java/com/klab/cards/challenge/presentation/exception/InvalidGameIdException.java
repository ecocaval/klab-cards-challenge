package com.klab.cards.challenge.presentation.exception;

import com.klab.cards.challenge.core.message.ErrorMessage;
import com.klab.cards.challenge.presentation.exception.base.BadRequestException;

import java.util.Map;

public class InvalidGameIdException extends BadRequestException {

    public InvalidGameIdException(String gameId) {
        super(ErrorMessage.ERROR_INVALID_GAME_ID.getMessage(), Map.of("gameId", gameId));
    }
}
