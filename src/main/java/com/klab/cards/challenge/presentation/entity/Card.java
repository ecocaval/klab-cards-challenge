package com.klab.cards.challenge.presentation.entity;

import com.klab.cards.challenge.presentation.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE card SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Card extends BaseEntity {

    @Column(nullable = false, unique = true, length = 2)
    private String rank;

    @Column(nullable = false, unique = true, length = 10)
    private String deckOfCardsRank;

    @Column(nullable = false)
    private int rankValue;
}
