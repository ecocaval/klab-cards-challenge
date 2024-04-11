package com.klab.cards.challenge.presentation.repository;

import com.github.javafaker.Faker;
import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.util.PlayerCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@DisplayName("Tests for Player Repository")
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("Save Persists Player When Successful")
    void save_PersistPlayer_WhenSuccessful() {

        var player = PlayerCreator.createPlayer();

        var playerSaved = this.playerRepository.save(player);

        Assertions.assertThat(playerSaved).isNotNull();
        Assertions.assertThat(playerSaved.getId()).isNotNull();
        Assertions.assertThat(playerSaved.getCreationDate()).isNotNull();
        Assertions.assertThat(playerSaved.isDeleted()).isEqualTo(Boolean.FALSE);
        Assertions.assertThat(playerSaved.getName()).isEqualTo(player.getName());
    }

    @Test
    @DisplayName("Save Updates Player When Successful")
    void save_UpdatePlayer_WhenSuccessful() {

        var playerSaved = this.playerRepository.save(PlayerCreator.createPlayer());

        playerSaved.setName(faker.name().name());
        playerSaved.setLastModifiedDate(LocalDateTime.now());

        var playerUpdated = this.playerRepository.save(playerSaved);

        Assertions.assertThat(playerUpdated).isNotNull();
        Assertions.assertThat(playerUpdated.getLastModifiedDate()).isNotNull();
        Assertions.assertThat(playerUpdated.getId()).isEqualTo(playerSaved.getId());
        Assertions.assertThat(playerSaved.getCreationDate()).isEqualTo(playerUpdated.getCreationDate());
        Assertions.assertThat(playerSaved.isDeleted()).isEqualTo(playerUpdated.isDeleted());
        Assertions.assertThat(playerSaved.getName()).isEqualTo(playerUpdated.getName());
    }

    @Test
    @DisplayName("Save Deletes Player When Successful")
    void delete_RemovesPlayer_WhenSuccessful() {

        var playerSaved = this.playerRepository.save(PlayerCreator.createPlayer());

        this.playerRepository.delete(playerSaved);

        Assertions.assertThat(this.playerRepository.findById(playerSaved.getId())).isEmpty();
    }

}