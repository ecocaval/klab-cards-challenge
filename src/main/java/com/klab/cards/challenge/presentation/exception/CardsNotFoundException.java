package com.klab.cards.challenge.presentation.exception;

import com.klab.cards.challenge.core.message.ErrorMessage;
import com.klab.cards.challenge.presentation.exception.base.NotFoundException;

public class CardsNotFoundException extends NotFoundException {

    public CardsNotFoundException() {
        super(ErrorMessage.ERROR_CARDS_NOT_FOUND_IN_DATABASE.getMessage());
    }
}
