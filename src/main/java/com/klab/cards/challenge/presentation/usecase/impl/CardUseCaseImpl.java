package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.core.message.ErrorMessage;
import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.exception.base.NotFoundException;
import com.klab.cards.challenge.presentation.repository.CardRepository;
import com.klab.cards.challenge.presentation.usecase.CardUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CardUseCaseImpl implements CardUseCase {

    private final CardRepository cardRepository;

    @Autowired
    public CardUseCaseImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Cacheable(value = "cardCache", key = "#deckOfCardsRank", cacheResolver = "cardCacheResolver")
    public Card findByDeckOfCardsRank(String deckOfCardsRank) {
        return this.cardRepository.findByDeckOfCardsRank(deckOfCardsRank).orElseThrow(() ->
                new NotFoundException(ErrorMessage.ERROR_CARD_NOT_FOUND_BY_DECK_OF_CARD_RANK.getMessage())
        );
    }
}
