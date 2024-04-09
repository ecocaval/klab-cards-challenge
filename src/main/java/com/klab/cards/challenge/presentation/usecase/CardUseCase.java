package com.klab.cards.challenge.presentation.usecase;

import com.klab.cards.challenge.presentation.entity.Card;

public interface CardUseCase {

    Card findByDeckOfCardsRank(String deckOfCardsRank);
}
