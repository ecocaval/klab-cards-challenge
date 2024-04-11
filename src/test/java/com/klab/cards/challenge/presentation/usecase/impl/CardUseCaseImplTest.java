package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.exception.CardNotFoundException;
import com.klab.cards.challenge.presentation.exception.CardsNotFoundException;
import com.klab.cards.challenge.presentation.repository.CardRepository;
import com.klab.cards.challenge.util.CardCreator;
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

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Card UseCase Implementation")
class CardUseCaseImplTest {

    @InjectMocks
    private CardUseCaseImpl cardUseCase;

    @Mock
    private CardRepository cardRepositoryMock;

    private final List<Card> staticCardList = CardCreator.createCardsListWithId();

    @BeforeEach
    void setup() {
        BDDMockito.when(cardRepositoryMock.findAll())
                .thenReturn(staticCardList);
    }

    @Test
    @DisplayName("Find All returns a list of cards when successful")
    void findAll_ReturnAListOfCards_WhenSuccessful() {

        var cards = this.cardUseCase.findAll();

        Assertions.assertThat(cards).isNotEmpty();
        Assertions.assertThat(cards).isEqualTo(staticCardList);
    }

    @Test
    @DisplayName("Find All throws CardsNotFoundException when no cards are found in database")
    void findAll_ThrowsCardsNotFoundException_WhenNoCardsAreFoundInDataBase() {

        BDDMockito.when(cardRepositoryMock.findAll())
                .thenReturn(List.of());

        Assertions.assertThatExceptionOfType(CardsNotFoundException.class)
                .isThrownBy(() -> this.cardUseCase.findAll());
    }

    @Test
    @DisplayName("Find By Deck Of Cards Ranks returns a card when sucessful")
    void findByDeckOfCardsRank_ReturnsCard_WhenSuccessful() {

        var card = this.cardUseCase.findByDeckOfCardsRank(staticCardList.get(0).getDeckOfCardsRank());

        Assertions.assertThat(card).isNotNull();
        Assertions.assertThat(card.getId()).isEqualTo(staticCardList.get(0).getId());
        Assertions.assertThat(card.getRank()).isEqualTo(staticCardList.get(0).getRank());
        Assertions.assertThat(card.getRankValue()).isEqualTo(staticCardList.get(0).getRankValue());
        Assertions.assertThat(card.getDeckOfCardsRank()).isEqualTo(staticCardList.get(0).getDeckOfCardsRank());
    }

    @Test
    @DisplayName("Find By Deck Of Cards Ranks throws CardNotFoundException when no card is found")
    void findByDeckOfCardsRank_ThrowsCardNotFoundException_WhenCardIsNotFoundByDeckOfCardsRank() {

        Assertions.assertThatExceptionOfType(CardNotFoundException.class)
                .isThrownBy(() -> this.cardUseCase.findByDeckOfCardsRank("INVALID RANK"));
    }

}