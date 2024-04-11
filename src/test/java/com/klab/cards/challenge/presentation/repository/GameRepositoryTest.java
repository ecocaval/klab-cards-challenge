package com.klab.cards.challenge.presentation.repository;

import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.util.GameCreator;
import com.klab.cards.challenge.util.HandCreator;
import com.klab.cards.challenge.util.PlayerCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

@DataJpaTest
@DisplayName("Tests for Game Repository")
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private final Random random = new Random();

    @Test
    @DisplayName("Save Persists Game When Successful")
    void save_PersistGame_WhenSuccessful() {

        var game = GameCreator.createGame();

        var gameSaved = this.gameRepository.save(game);

        Assertions.assertThat(gameSaved).isNotNull();
        Assertions.assertThat(gameSaved.getId()).isNotNull();
        Assertions.assertThat(gameSaved.getCreationDate()).isNotNull();
        Assertions.assertThat(gameSaved.getPlayers()).isNotEmpty();
        Assertions.assertThat(gameSaved.getPlayers()).size().isEqualTo(Game.NUMBER_OF_PLAYERS);
        Assertions.assertThat(gameSaved.getHands()).size().isEqualTo(Game.NUMBER_OF_PLAYERS);
        Assertions.assertThat(gameSaved.getWinners()).isNotNull();
        Assertions.assertThat(gameSaved.isDeleted()).isEqualTo(Boolean.FALSE);
    }

    @Test
    @DisplayName("Save Updates Games When Successful")
    void save_UpdateGame_WhenSuccessful() {

        var gameSaved = this.gameRepository.save(GameCreator.createGame());

        var listOfNewPlayers = PlayerCreator.createPlayerList();

        gameSaved.setPlayers(new HashSet<>(listOfNewPlayers));
        gameSaved.setWinners(new HashSet<>(List.of(listOfNewPlayers.get(random.nextInt(4)))));
        gameSaved.setHands(new HashSet<>());

        gameSaved.getPlayers().forEach(player -> {
                    var hand = HandCreator.createHand();
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
        Assertions.assertThat(gameUpdated.getPlayers()).size().isEqualTo(Game.NUMBER_OF_PLAYERS);
        Assertions.assertThat(gameUpdated.getPlayers()).isEqualTo(gameSaved.getPlayers());
        Assertions.assertThat(gameUpdated.getHands()).size().isEqualTo(Game.NUMBER_OF_PLAYERS);
        Assertions.assertThat(gameUpdated.getHands()).isEqualTo(gameSaved.getHands());
        Assertions.assertThat(gameUpdated.getWinners()).isNotEmpty();
        Assertions.assertThat(gameUpdated.getWinners()).isEqualTo(gameSaved.getWinners());
    }

    @Test
    @DisplayName("Save Deletes Game When Successful")
    void delete_RemovesGame_WhenSuccessful() {

        var gameSaved = this.gameRepository.save(GameCreator.createGame());

        this.gameRepository.delete(gameSaved);

        Assertions.assertThat(this.gameRepository.findById(gameSaved.getId())).isEmpty();
    }
}