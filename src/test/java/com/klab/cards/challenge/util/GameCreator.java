package com.klab.cards.challenge.util;

import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Hand;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class GameCreator {

    public static Game createGame() {

        Game game = new Game();

        game.setPlayers(new HashSet<>(PlayerCreator.createPlayerList()));

        Set<Hand> gameHands = new HashSet<>();

        game.getPlayers().forEach(player -> {
                    var hand = HandCreator.createHand();
                    hand.setPlayer(player);
                    hand.setGame(game);
                    gameHands.add(hand);
                }
        );

        game.setHands(gameHands);

        int highestScore = game.getHands().stream()
                .max(Comparator.comparingInt(Hand::getScore))
                .map(Hand::getScore)
                .orElseThrow();

        game.setWinners(
                game.getHands()
                        .stream()
                        .filter(hand -> hand.getScore() >= highestScore)
                        .map(Hand::getPlayer)
                        .collect(Collectors.toSet())
        );

        return game;
    }
}
