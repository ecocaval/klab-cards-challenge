package com.klab.cards.challenge.util;

import com.github.javafaker.Faker;
import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Player;

import java.util.List;
import java.util.stream.IntStream;

public class PlayerCreator {

    private static final Faker faker = new Faker();

    public static Player createPlayer() {
        return Player.builder()
                .name(faker.name().name())
                .build();
    }

    public static List<Player> createPlayerList() {
        return IntStream.range(0, Game.NUMBER_OF_PLAYERS)
                .mapToObj(i -> createPlayer())
                .toList();
    }
}
