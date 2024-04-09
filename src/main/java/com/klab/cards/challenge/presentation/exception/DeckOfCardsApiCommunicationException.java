package com.klab.cards.challenge.presentation.exception;

import com.klab.cards.challenge.core.message.ErrorMessage;
import com.klab.cards.challenge.presentation.exception.base.ServiceUnavailableException;

public class DeckOfCardsApiCommunicationException extends ServiceUnavailableException {

    public DeckOfCardsApiCommunicationException() {
        super(ErrorMessage.ERROR_DECK_OF_CARDS_API_COMMUNICATION_ERROR.getMessage());
    }
}
