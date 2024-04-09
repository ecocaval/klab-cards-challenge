package com.klab.cards.challenge.presentation.usecase;

import com.klab.cards.challenge.presentation.entity.Card;

import java.util.List;

public interface CardUseCase {

    List<Card> findAll();

    Card findByDeckOfCardsRank(String deckOfCardsRank);
}
