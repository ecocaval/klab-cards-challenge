package com.klab.cards.challenge.util;

import com.github.javafaker.Faker;
import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.entity.Game;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class CardCreator {

    private static final Faker faker = new Faker();

    private static final Random random = new Random();

    public static Card createCard() {

        var randomRank = faker.numerify("##");

        return Card.builder()
                .rank(randomRank)
                .deckOfCardsRank(randomRank)
                .rankValue(random.nextInt(Game.NUMBER_OF_CARDS))
                .build();
    }

    public static List<Card> createCardsList() {
        return IntStream.range(0, Game.NUMBER_OF_CARDS)
                .mapToObj(i -> createCard())
                .toList();
    }

    public static Card createCardWithId() {

        var randomRank = faker.numerify("##");

        return Card.builder()
                .id(UUID.randomUUID())
                .rank(randomRank)
                .deckOfCardsRank(randomRank)
                .rankValue(random.nextInt(Game.NUMBER_OF_CARDS))
                .build();
    }

    public static List<Card> createCardsListWithId() {
        return IntStream.range(0, Game.NUMBER_OF_CARDS)
                .mapToObj(i -> createCardWithId())
                .toList();
    }
}
