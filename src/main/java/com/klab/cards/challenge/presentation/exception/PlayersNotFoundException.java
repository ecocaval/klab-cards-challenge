package com.klab.cards.challenge.presentation.exception;

import com.klab.cards.challenge.core.message.ErrorMessage;
import com.klab.cards.challenge.presentation.exception.base.NotFoundException;

import java.util.Map;

public class PlayersNotFoundException extends NotFoundException {

    public PlayersNotFoundException() {
        super(ErrorMessage.ERROR_PLAYERS_NOT_FOUND_IN_DATABASE.getMessage());
    }
}
