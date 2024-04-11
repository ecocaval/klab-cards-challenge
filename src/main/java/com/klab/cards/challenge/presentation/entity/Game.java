package com.klab.cards.challenge.presentation.entity;

import com.klab.cards.challenge.presentation.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Entity
@SuperBuilder
@Getter
@Setter
@SQLDelete(sql = "UPDATE game SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Game extends BaseEntity {

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "game_players",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<Player> players;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "game_winners",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<Player> winners;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Set<Hand> hands;

    public static final int MAXIMUM_NUMBER_OF_PLAYERS = 4;

    public static final int MAXIMUM_NUMBER_OF_CARDS_PER_PLAYER = 5;

    public Game() {
        this.winners = new HashSet<>();
        this.hands= new HashSet<>();
    }
}