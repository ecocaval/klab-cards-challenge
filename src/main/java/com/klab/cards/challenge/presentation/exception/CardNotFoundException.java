package com.klab.cards.challenge.presentation.exception;

import com.klab.cards.challenge.core.message.ErrorMessage;
import com.klab.cards.challenge.presentation.exception.base.NotFoundException;

import java.util.Map;

public class CardNotFoundException extends NotFoundException {

    public CardNotFoundException(String deckOfCardsRank) {
        super(ErrorMessage.ERROR_CARD_NOT_FOUND_BY_DECK_OF_CARD_RANK.getMessage(), Map.of("deckOfCardsRank", deckOfCardsRank));
    }
}
