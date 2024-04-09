package com.klab.cards.challenge.presentation.entity;

import com.klab.cards.challenge.presentation.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Hand extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToMany
    @JoinTable(
            name = "hand_cards",
            joinColumns = @JoinColumn(name = "hand_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cards;

    @Column(nullable = false)
    private Integer score;

    public Hand(Player player, Game game) {
        this.player = player;
        this.game = game;
        this.cards = new ArrayList<>();
        this.score = 0;
    }

    public void incrementScore(int increment) {
        this.score = this.score + increment;
    }
}