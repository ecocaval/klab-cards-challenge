package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.exception.CardNotFoundException;
import com.klab.cards.challenge.presentation.exception.CardsNotFoundException;
import com.klab.cards.challenge.presentation.repository.CardRepository;
import com.klab.cards.challenge.presentation.usecase.CardUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardUseCaseImpl implements CardUseCase {

    private final CardRepository cardRepository;

    @Autowired
    public CardUseCaseImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Cacheable(value = "cardsCache", cacheResolver = "cardsCacheResolver")
    public List<Card> findAll() {

        List<Card> cards = this.cardRepository.findAll();

        if(cards.isEmpty()) {
            throw new CardsNotFoundException();
        }

        return cards;
    }

    @Override
    @Cacheable(value = "cardCache", key = "#deckOfCardsRank", cacheResolver = "cardCacheResolver")
    public Card findByDeckOfCardsRank(String deckOfCardsRank) {
        return this.findAll()
                .stream()
                .filter(card -> card.getDeckOfCardsRank().equals(deckOfCardsRank))
                .findFirst()
                .orElseThrow(() ->  new CardNotFoundException(deckOfCardsRank));
    }
}
