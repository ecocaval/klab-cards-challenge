package com.klab.cards.challenge.util;

import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Hand;

import java.util.Random;

public class HandCreator {

    private static final Random random = new Random();

    public static Hand createHand() {

        var randomOffset = random.nextInt(9);

        var randomCardsHand = CardCreator.createCardList().subList(
                randomOffset,
                Game.NUMBER_OF_CARDS_PER_PLAYER + randomOffset
        );

        return Hand.builder()
                .cards(randomCardsHand)
                .score(randomCardsHand.stream()
                        .reduce(0, (total, card) -> total + card.getRankValue(), Integer::sum)
                )
                .build();
    }
}
