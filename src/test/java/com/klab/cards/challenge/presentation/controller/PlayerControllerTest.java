package com.klab.cards.challenge.presentation.controller;

import com.klab.cards.challenge.core.data.response.GameResponse;
import com.klab.cards.challenge.core.data.response.PlayerResponse;
import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Hand;
import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.presentation.exception.InvalidPlayerIdException;
import com.klab.cards.challenge.presentation.usecase.GameUseCase;
import com.klab.cards.challenge.presentation.usecase.PlayerUseCase;
import com.klab.cards.challenge.util.GameCreator;
import com.klab.cards.challenge.util.PlayerCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.IntStream;


@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Player Controller")
class PlayerControllerTest {

    @InjectMocks
    private PlayerController playerController;

    @Mock
    private PlayerUseCase playerUseCaseMock;

    @Mock
    private GameUseCase gameUseCaseMock;

    private final Random random = new Random();

    private final Player staticPlayer = PlayerCreator.createPlayerWithId();

    private final List<Player> staticPlayers = PlayerCreator.createPlayerListWithId();

    @BeforeEach
    void setup() {

        List<Game> gameList = IntStream.range(0, random.nextInt(10))
                .mapToObj(i -> GameCreator.createGame()).toList();

        gameList.forEach(game -> {
                    game.setWinners(new HashSet<>(List.of(staticPlayer)));

                    int highestScore = game.getHands().stream()
                            .max(Comparator.comparingInt(Hand::getScore))
                            .map(Hand::getScore)
                            .orElseThrow();

                    for (Hand hand : game.getHands()) {
                        if (hand.getScore() >= highestScore) {
                            hand.setPlayer(staticPlayer);
                            break;
                        }
                    }
                }
        );

        Page<Game> gamePage = new PageImpl<>(gameList, PageRequest.of(0, 10), gameList.size());

        BDDMockito.when(gameUseCaseMock.findAllGamesWonByPlayerPageable(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(gamePage);

        BDDMockito.when(playerUseCaseMock.findById(ArgumentMatchers.any()))
                .thenReturn(staticPlayer);

        BDDMockito.when(playerUseCaseMock.findAll())
                .thenReturn(staticPlayers);
    }

    @Test
    @DisplayName("Find all returns a list of players when successful")
    void findAll_ReturnAListOfPlayers_WhenSuccessful() {

        Set<PlayerResponse> players = playerController.findAll().getBody();

        Assertions.assertThat(players).isNotEmpty();
        Assertions.assertThat(players).size().isEqualTo(Game.NUMBER_OF_PLAYERS);

        List<PlayerResponse> playerResponseList = new ArrayList<>(players);

        Assertions.assertThat(playerResponseList.get(0).getName()).isNotNull();
        Assertions.assertThat(playerResponseList.get(0).getId()).isNotNull();
    }

    @Test
    @DisplayName("Find all games won by player id throws InvalidPlayerIdException when playerId is not valid")
    void findAllGamesWonById_ThrowsInvalidPlayerIdException_WhenPlayerIdIsNotValid() {
        var INVALID_UUID = "UUID";
        Assertions.assertThatExceptionOfType(InvalidPlayerIdException.class)
                .isThrownBy(() -> playerController.findAllGamesWonById(INVALID_UUID, Pageable.unpaged()));
    }

    @Test
    @DisplayName("Find all games won by id returns a list of games won when successful")
    void findAllGamesWonById_ReturnAListOfGamesWon_WhenSuccessful() {

        Page<GameResponse> gameResponsesPage = playerController.findAllGamesWonById(
                String.valueOf(staticPlayer.getId()), Pageable.unpaged()
        ).getBody();

        Assertions.assertThat(gameResponsesPage).isNotNull();
        Assertions.assertThat(gameResponsesPage.getContent()).isNotNull();

        gameResponsesPage.getContent().forEach(gameResponse ->
                Assertions.assertThat(gameResponse.getWinners().get(0).getId()).isEqualTo(staticPlayer.getId())
        );
    }

}