package com.klab.cards.challenge.presentation.repository;

import com.github.javafaker.Faker;
import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.util.CardCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@DisplayName("Tests for Card Repository")
class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;

    private final Faker faker = new Faker();

    @BeforeEach
    void cleanDb() {
        this.cardRepository.deleteAll();
    }

    @Test
    @DisplayName("Save Persists Card When Successful")
    void save_PersistCard_WhenSuccessful() {

        var card = CardCreator.createCard();

        var cardSaved = this.cardRepository.save(card);

        Assertions.assertThat(cardSaved).isNotNull();
        Assertions.assertThat(cardSaved.getId()).isNotNull();
        Assertions.assertThat(cardSaved.getCreationDate()).isNotNull();
        Assertions.assertThat(card.isDeleted()).isEqualTo(Boolean.FALSE);
        Assertions.assertThat(card.getRank()).isEqualTo(cardSaved.getRank());
        Assertions.assertThat(card.getDeckOfCardsRank()).isEqualTo(cardSaved.getDeckOfCardsRank());
        Assertions.assertThat(card.getRankValue()).isEqualTo(cardSaved.getRankValue());
    }

    @Test
    @DisplayName("Save Updates Card When Successful")
    void save_UpdateCard_WhenSuccessful() {

        var cardSaved = this.cardRepository.save(CardCreator.createCard());

        cardSaved.setRank("2");
        cardSaved.setDeckOfCardsRank("2");
        cardSaved.setRankValue(2);
        cardSaved.setLastModifiedDate(LocalDateTime.now());

        var cardUpdated = this.cardRepository.save(cardSaved);

        Assertions.assertThat(cardUpdated).isNotNull();
        Assertions.assertThat(cardUpdated.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(cardSaved.getId()).isEqualTo(cardUpdated.getId());
        Assertions.assertThat(cardSaved.getCreationDate()).isEqualTo(cardUpdated.getCreationDate());
        Assertions.assertThat(cardSaved.isDeleted()).isEqualTo(cardUpdated.isDeleted());
        Assertions.assertThat(cardSaved.getRank()).isEqualTo(cardUpdated.getRank());
        Assertions.assertThat(cardSaved.getDeckOfCardsRank()).isEqualTo(cardUpdated.getDeckOfCardsRank());
        Assertions.assertThat(cardSaved.getRankValue()).isEqualTo(cardUpdated.getRankValue());
    }

    @Test
    @DisplayName("Delete Removes Card When Successful")
    void delete_RemovesCard_WhenSuccessful() {

        var cardSaved = this.cardRepository.save(CardCreator.createCard());

        this.cardRepository.delete(cardSaved);

        Assertions.assertThat(this.cardRepository.findById(cardSaved.getId())).isEmpty();
    }

    @Test
    @DisplayName("Get Finds Card By Deck of Card Rank When Successful")
    void get_FindCardByDeckOfCardsRank_WhenSuccessful() {

        var cardSaved = this.cardRepository.save(CardCreator.createCard());

        Assertions.assertThat(this.cardRepository.findByDeckOfCardsRank(cardSaved.getDeckOfCardsRank())).isNotEmpty();

        Assertions.assertThat(this.cardRepository.findByDeckOfCardsRank(faker.name().name())).isEmpty();
    }

}