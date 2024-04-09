package com.klab.cards.challenge.presentation.usecase;

import com.klab.cards.challenge.presentation.entity.Hand;
import com.klab.cards.challenge.presentation.entity.Player;

import java.util.Set;

public interface HandUseCase {

    Set<Player> getWinnersFromHands(Set<Hand> hands);
}
