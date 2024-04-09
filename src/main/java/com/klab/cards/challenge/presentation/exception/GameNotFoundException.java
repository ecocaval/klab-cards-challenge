package com.klab.cards.challenge.presentation.exception;

import com.klab.cards.challenge.core.message.ErrorMessage;
import com.klab.cards.challenge.presentation.exception.base.BadRequestException;
import com.klab.cards.challenge.presentation.exception.base.NotFoundException;

import java.util.Map;

public class GameNotFoundException extends NotFoundException {

    public GameNotFoundException(String gameId) {
        super(ErrorMessage.ERROR_GAME_NOT_FOUND_BY_ID.getMessage(), Map.of("gameId", gameId));
    }
}
