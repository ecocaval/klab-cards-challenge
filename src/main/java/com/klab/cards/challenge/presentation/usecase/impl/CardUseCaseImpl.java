package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.usecase.CardUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardUseCaseImpl implements CardUseCase {

    @Override
    public List<Card> findAll() {
        return null;
    }

    @Override
    public List<Card> findByRank(String rank) {
        return null;
    }
}
