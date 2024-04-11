package com.klab.cards.challenge.util;

import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Hand;
import com.klab.cards.challenge.presentation.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameCreator {

    private static final Random random = new Random();

    public static Game createGame() {

        Game game = new Game();

        game.setId(UUID.randomUUID());

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

    public static Page<Game> createGamePage() {

        List<Game> gameList = IntStream.range(0, random.nextInt(10))
                .mapToObj(i -> GameCreator.createGame()).toList();

        return new PageImpl<>(gameList, PageRequest.of(0, 10), gameList.size());
    }

    public static Page<Game> createPageOfGamesWonByPlayer(Player player) {
        List<Game> gameList = IntStream.range(0, random.nextInt(10))
                .mapToObj(i -> GameCreator.createGame()).toList();

        gameList.forEach(game -> {
                    game.setWinners(new HashSet<>(List.of(player)));

                    int highestScore = game.getHands().stream()
                            .max(Comparator.comparingInt(Hand::getScore))
                            .map(Hand::getScore)
                            .orElseThrow();

                    for (Hand hand : game.getHands()) {
                        if (hand.getScore() >= highestScore) {
                            hand.setPlayer(player);
                            break;
                        }
                    }
                }
        );

        return new PageImpl<>(gameList, PageRequest.of(0, 10), gameList.size());
    }
}
