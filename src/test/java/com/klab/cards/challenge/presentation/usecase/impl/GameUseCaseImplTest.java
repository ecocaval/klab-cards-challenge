package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.core.data.response.DeckOfCardsApiDeckResponse;
import com.klab.cards.challenge.core.data.response.DeckOfCardsApiDrawACardResponse;
import com.klab.cards.challenge.presentation.client.DeckOfCardsApiClient;
import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.presentation.exception.CardsNotFoundException;
import com.klab.cards.challenge.presentation.exception.DeckOfCardsApiCommunicationException;
import com.klab.cards.challenge.presentation.exception.GameNotFoundException;
import com.klab.cards.challenge.presentation.repository.CardRepository;
import com.klab.cards.challenge.presentation.repository.GameRepository;
import com.klab.cards.challenge.presentation.usecase.CardUseCase;
import com.klab.cards.challenge.presentation.usecase.HandUseCase;
import com.klab.cards.challenge.presentation.usecase.PlayerUseCase;
import com.klab.cards.challenge.util.CardCreator;
import com.klab.cards.challenge.util.GameCreator;
import com.klab.cards.challenge.util.PlayerCreator;
import feign.FeignException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Game UseCase Implementation")
class GameUseCaseImplTest {

    @InjectMocks
    private GameUseCaseImpl gameUseCase;

    @Mock
    private PlayerUseCase playerUseCaseMock;

    @Mock
    private CardUseCase cardUseCaseMock;

    @Mock
    private HandUseCase handUseCaseMock;

    @Mock
    private DeckOfCardsApiClient deckOfCardsApiClientMock;

    @Mock
    private GameRepository gameRepositoryMock;

    private final Player staticPlayer = PlayerCreator.createPlayerWithId();

    private final List<Player> staticPlayers = PlayerCreator.createPlayerListWithId();

    private final Game staticGame = GameCreator.createGame();

    private final Card staticCard = CardCreator.createCardWithId();

    @BeforeEach
    void setup() {

        final var DECK_ID = UUID.randomUUID().toString();

        BDDMockito.when(gameRepositoryMock.findAllByOrderByCreationDateDesc(ArgumentMatchers.any()))
                .thenReturn(GameCreator.createGamePage());

        BDDMockito.when(gameRepositoryMock.findAllByWinnersContainingOrderByCreationDateDesc(
                        ArgumentMatchers.any(), ArgumentMatchers.any())
                )
                .thenReturn(GameCreator.createPageOfGamesWonByPlayer(staticPlayer));

        BDDMockito.when(gameRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(staticGame));

        BDDMockito.when(playerUseCaseMock.findAll())
                .thenReturn(staticPlayers);

        BDDMockito.when(deckOfCardsApiClientMock.getNewDeck())
                .thenReturn(DeckOfCardsApiDeckResponse.builder()
                        .deckId(DECK_ID)
                        .build());

        BDDMockito.when(deckOfCardsApiClientMock.shuffleDeck(ArgumentMatchers.any()))
                        .thenReturn(DeckOfCardsApiDeckResponse.builder()
                                .deckId(DECK_ID)
                                .build()
                        );

        BDDMockito.when(cardUseCaseMock.findByDeckOfCardsRank(ArgumentMatchers.any()))
                .thenReturn(staticCard);

        BDDMockito.when(deckOfCardsApiClientMock.drawACardFromTheDeck(ArgumentMatchers.any()))
                .thenReturn(DeckOfCardsApiDrawACardResponse.builder()
                        .cards(List.of(
                                DeckOfCardsApiDrawACardResponse.DeckOfCardsApiCardResponse.builder()
                                        .deckOfCardsRank(staticCard.getDeckOfCardsRank())
                                        .build()
                        ))
                        .build()
                );

        BDDMockito.when(handUseCaseMock.getWinnersFromHands(ArgumentMatchers.any()))
                .thenReturn(new HashSet<>(List.of(staticPlayers.get(0))));

        BDDMockito.when(gameRepositoryMock.save(ArgumentMatchers.any()))
                .thenReturn(staticGame);
    }

    @Test
    @DisplayName("Find all pageable returns a page of games when successful")
    void findAllPageable_ReturnsAPageOfGames_WhenSuccessful() {

        var gamesPage = this.gameUseCase.findAllPageable(Pageable.unpaged());

        Assertions.assertThat(gamesPage).isNotNull();
        Assertions.assertThat(gamesPage.getContent()).isNotNull();
    }

    @Test
    @DisplayName("Find all games won by player pageable returns a page of games when successful")
    void findAllGamesWonByPlayerPageable_ReturnsAPageOfGames_WhenSuccessful() {

        var gamesPage = this.gameUseCase.findAllGamesWonByPlayerPageable(staticPlayer, Pageable.unpaged());

        Assertions.assertThat(gamesPage).isNotNull();
        Assertions.assertThat(gamesPage.getContent()).isNotNull();

        gamesPage.getContent().forEach(game -> {
            List<Player> winners = new ArrayList<>(game.getWinners());
                    Assertions.assertThat(winners.get(0).getId()).isEqualTo(staticPlayer.getId());
        });
    }

    @Test
    @DisplayName("Find by id returns game when successful")
    void findById_ReturnsGame_WhenSuccessful() {

        var game = this.gameUseCase.findById(String.valueOf(staticGame.getId()));

        Assertions.assertThat(game).isNotNull();
        Assertions.assertThat(game.getId()).isEqualTo(staticGame.getId());
        Assertions.assertThat(game.getHands()).isEqualTo(staticGame.getHands());
        Assertions.assertThat(game.getPlayers()).isEqualTo(staticGame.getPlayers());
        Assertions.assertThat(game.getWinners()).isEqualTo(staticGame.getWinners());
    }

    @Test
    @DisplayName("Find by id throws GameNotFoundException when game is not found by id")
    void findById_ThrowsGameNotFoundException_WhenGameIsNotFoundById() {

        BDDMockito.when(gameRepositoryMock.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(GameNotFoundException.class)
                .isThrownBy(() -> this.gameUseCase.findById(String.valueOf(staticGame.getId())));
    }

    @Test
    @DisplayName("Create persists game when successful")
    void create_PersistsGame_WhenSuccessful() {

        var game = this.gameUseCase.create();

        Assertions.assertThat(game).isNotNull();
        Assertions.assertThat(game.getPlayers()).isEqualTo(staticGame.getPlayers());
        Assertions.assertThat(game.getWinners()).isNotEmpty();
        Assertions.assertThat(game.getWinners()).isEqualTo(staticGame.getWinners());
        Assertions.assertThat(game.getHands()).isNotEmpty();
        Assertions.assertThat(game.getHands()).isEqualTo(staticGame.getHands());
    }
}