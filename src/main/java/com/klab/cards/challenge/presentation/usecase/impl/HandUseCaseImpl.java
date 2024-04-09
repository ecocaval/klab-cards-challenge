package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.presentation.entity.Hand;
import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.presentation.usecase.HandUseCase;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HandUseCaseImpl implements HandUseCase {

    @Override
    public Set<Player> getWinnersFromHands(Set<Hand> hands) {

        if(hands.isEmpty()) {
            return new HashSet<>();
        }

        int highestScore = hands.stream()
                .max(Comparator.comparingInt(Hand::getScore))
                .map(Hand::getScore)
                .orElseThrow();

        return hands
                .stream()
                .filter(hand -> hand.getScore() >= highestScore)
                .map(Hand::getPlayer)
                .collect(Collectors.toSet());

    }
}
