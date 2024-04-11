package com.klab.cards.challenge.presentation.controller;

import com.klab.cards.challenge.core.data.response.GameResponse;
import com.klab.cards.challenge.core.data.response.PlayerResponse;
import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.exception.InvalidGameIdException;
import com.klab.cards.challenge.presentation.exception.InvalidPlayerIdException;
import com.klab.cards.challenge.presentation.usecase.GameUseCase;
import com.klab.cards.challenge.util.GameCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Player Controller")
class GameControllerTest {

    @InjectMocks
    private GameController gameController;

    @Mock
    private GameUseCase gameUseCaseMock;

    private final Game staticGame = GameCreator.createGame();

    @BeforeEach
    void setup() {
        BDDMockito.when(gameUseCaseMock.findAllPageable(ArgumentMatchers.any()))
                .thenReturn(GameCreator.createGamePage());

        BDDMockito.when(gameUseCaseMock.findById(ArgumentMatchers.any()))
                .thenReturn(staticGame);

        BDDMockito.when(gameUseCaseMock.create())
                .thenReturn(staticGame);
    }

    @Test
    @DisplayName("Find all pageable returns a page of games")
    void findAllPageable_ReturnsAPageOfGames_WhenSuccessful() {

        Page<GameResponse> games = gameController.findAllPageable(Pageable.unpaged()).getBody();

        Assertions.assertThat(games).isNotEmpty();

        Assertions.assertThat(games).isNotNull();
        Assertions.assertThat(games.getContent()).isNotEmpty();
        Assertions.assertThat(games.getContent().get(0).getWinners()).isNotEmpty();
        Assertions.assertThat(games.getContent().get(0).getHands()).isNotEmpty();
    }

    @Test
    @DisplayName("Find by id throws InvalidGameIdException when game id is not valid")
    void findById_ThrowsInvalidGameIdException_WhenGameIdIsNotValid() {
        var INVALID_UUID = "UUID";
        Assertions.assertThatExceptionOfType(InvalidGameIdException.class)
                .isThrownBy(() -> gameController.findById(INVALID_UUID));
    }

    @Test
    @DisplayName("Find by id returns a game when successful")
    void findById_ReturnsAGame_WhenSuccessful() {

        var gameResponse = gameController.findById(String.valueOf(staticGame.getId())).getBody();

        Assertions.assertThat(gameResponse).isNotNull();
        Assertions.assertThat(gameResponse.getHands()).isNotEmpty();
        Assertions.assertThat(gameResponse.getWinners()).isNotEmpty();
        Assertions.assertThat(gameResponse.getId()).isEqualTo(staticGame.getId());
    }

    @Test
    @DisplayName("Create persists a game when successful")
    void create_PersistsAGame_WhenSuccessful() {

        var gameResponse = gameController.create().getBody();

        Assertions.assertThat(gameResponse).isNotNull();
        Assertions.assertThat(gameResponse.getHands()).isNotEmpty();
        Assertions.assertThat(gameResponse.getWinners()).isNotEmpty();
        Assertions.assertThat(gameResponse.getId()).isEqualTo(staticGame.getId());
    }

}