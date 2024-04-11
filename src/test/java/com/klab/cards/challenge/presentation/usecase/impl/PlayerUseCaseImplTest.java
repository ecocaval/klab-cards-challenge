package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.presentation.exception.CardNotFoundException;
import com.klab.cards.challenge.presentation.exception.CardsNotFoundException;
import com.klab.cards.challenge.presentation.exception.PlayerNotFoundException;
import com.klab.cards.challenge.presentation.exception.PlayersNotFoundException;
import com.klab.cards.challenge.presentation.repository.PlayerRepository;
import com.klab.cards.challenge.util.CardCreator;
import com.klab.cards.challenge.util.PlayerCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Player UseCase Implementation")
class PlayerUseCaseImplTest {

    @InjectMocks
    private PlayerUseCaseImpl playerUseCase;

    @Mock
    private PlayerRepository playerRepositoryMock;

    private final List<Player> staticPlayersList = PlayerCreator.createPlayerListWithId();

    @BeforeEach
    void setup() {
        BDDMockito.when(playerRepositoryMock.findAll())
                .thenReturn(staticPlayersList);
    }

    @Test
    @DisplayName("Find all returns a list of players when successful")
    void findAll_ReturnAListOfPlayers_WhenSuccessful() {

        var players = this.playerUseCase.findAll();

        Assertions.assertThat(players).isNotEmpty();
        Assertions.assertThat(players).isEqualTo(staticPlayersList);
    }

    @Test
    @DisplayName("Find all throws PlayersNotFoundException when no players are found in database")
    void findAll_ThrowsPlayersNotFoundException_WhenNoCardsAreFoundInDataBase() {

        BDDMockito.when(playerUseCase.findAll())
                .thenReturn(List.of());

        Assertions.assertThatExceptionOfType(PlayersNotFoundException.class)
                .isThrownBy(() -> this.playerUseCase.findAll());
    }

    @Test
    @DisplayName("Find by id returns a player when successful")
    void findById_ReturnsPlayer_WhenSuccessful() {

        var player = this.playerUseCase.findById(String.valueOf(staticPlayersList.get(0).getId()));

        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getId()).isEqualTo(staticPlayersList.get(0).getId());
        Assertions.assertThat(player.getName()).isEqualTo(staticPlayersList.get(0).getName());
    }

    @Test
    @DisplayName("Find by id throws PlayerNotFoundException when player is not found by id")
    void findById_ThrowsPlayerNotFoundException_WhenPlayersIsNotFoundById() {

        Assertions.assertThatExceptionOfType(PlayerNotFoundException.class)
                .isThrownBy(() -> this.playerUseCase.findById(UUID.randomUUID().toString()));
    }
}