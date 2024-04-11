package com.klab.cards.challenge.presentation.repository;

import com.github.javafaker.Faker;
import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Hand;
import com.klab.cards.challenge.presentation.entity.Player;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DataJpaTest
@DisplayName("Tests for Player Repository")
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private final Faker faker = new Faker();

    private final Random random = new Random();

    @Test
    @DisplayName("Save Persists Game When Successful")
    void save_PersistGame_WhenSuccessful() {

        var game = this.createGame();

        var gameSaved = this.gameRepository.save(game);

        Assertions.assertThat(gameSaved).isNotNull();
        Assertions.assertThat(gameSaved.getId()).isNotNull();
        Assertions.assertThat(gameSaved.getCreationDate()).isNotNull();
        Assertions.assertThat(gameSaved.getPlayers()).isNotEmpty();
        Assertions.assertThat(gameSaved.getPlayers()).size().isEqualTo(Game.MAXIMUM_NUMBER_OF_PLAYERS);
        Assertions.assertThat(gameSaved.getHands()).size().isEqualTo(Game.MAXIMUM_NUMBER_OF_PLAYERS);
        Assertions.assertThat(gameSaved.getWinners()).isNotNull();
        Assertions.assertThat(gameSaved.isDeleted()).isEqualTo(Boolean.FALSE);
    }

    @Test
    @DisplayName("Save Updates Games When Successful")
    void save_UpdateGame_WhenSuccessful() {

        var gameSaved = this.gameRepository.save(this.createGame());

        var listOfNewPlayers = IntStream.range(0, Game.MAXIMUM_NUMBER_OF_PLAYERS)
                .mapToObj(i -> createPlayer())
                .toList();

        gameSaved.setPlayers(new HashSet<>(listOfNewPlayers));
        gameSaved.setWinners(new HashSet<>(List.of(listOfNewPlayers.get(random.nextInt(4)))));
        gameSaved.setHands(new HashSet<>());

        gameSaved.getPlayers().forEach(player -> {
                    var hand = createHand();
                    hand.setPlayer(player);
                    hand.setGame(gameSaved);
                    gameSaved.getHands().add(hand);
                }
        );

        var gameUpdated = this.gameRepository.save(gameSaved);

        Assertions.assertThat(gameUpdated).isNotNull();
        Assertions.assertThat(gameUpdated.getId()).isNotNull();
        Assertions.assertThat(gameUpdated.getId()).isEqualTo(gameSaved.getId());
        Assertions.assertThat(gameUpdated.getCreationDate()).isNotNull();
        Assertions.assertThat(gameUpdated.getCreationDate()).isEqualTo(gameSaved.getCreationDate());
        Assertions.assertThat(gameUpdated.getPlayers()).isNotEmpty();
        Assertions.assertThat(gameUpdated.getPlayers()).size().isEqualTo(Game.MAXIMUM_NUMBER_OF_PLAYERS);
        Assertions.assertThat(gameUpdated.getPlayers()).isEqualTo(gameSaved.getPlayers());
        Assertions.assertThat(gameUpdated.getHands()).size().isEqualTo(Game.MAXIMUM_NUMBER_OF_PLAYERS);
        Assertions.assertThat(gameUpdated.getHands()).isEqualTo(gameSaved.getHands());
        Assertions.assertThat(gameUpdated.getWinners()).isNotEmpty();
        Assertions.assertThat(gameUpdated.getWinners()).isEqualTo(gameSaved.getWinners());
    }

    @Test
    @DisplayName("Save Deletes Game When Successful")
    void delete_RemovesGame_WhenSuccessful() {

        var gameSaved = this.gameRepository.save(createGame());

        this.gameRepository.delete(gameSaved);

        Assertions.assertThat(this.gameRepository.findById(gameSaved.getId())).isEmpty();
    }

    private Game createEmptyGame() {
        return new Game();
    }

    private List<Player> listAllPlayers() {
        return this.playerRepository.findAll();
    }

    private Hand createHand() {

        var randomOffset = random.nextInt(9);

        var randomCardsHand = this.cardRepository.findAll().subList(
                randomOffset,
                Game.MAXIMUM_NUMBER_OF_CARDS_PER_PLAYER + randomOffset
        );

        return Hand.builder()
                .cards(randomCardsHand)
                .score(randomCardsHand.stream()
                        .reduce(0, (total, card) -> total + card.getRankValue(), Integer::sum)
                )
                .build();
    }

    private Game createGame() {

        Game game = createEmptyGame();

        game.setPlayers(new HashSet<>(listAllPlayers()));

        Set<Hand> gameHands = new HashSet<>();

        game.getPlayers().forEach(player -> {
                    var hand = createHand();
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

    private Player createPlayer() {
        return this.playerRepository.save(
                Player
                        .builder()
                        .name(faker.name().name())
                        .build()
        );
    }

}