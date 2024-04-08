package com.klab.cards.challenge.presentation.entity;

import com.klab.cards.challenge.presentation.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class Card extends BaseEntity {

    @Column(nullable = false, unique = true, length = 2)
    private String rank;

    @Column(nullable = false)
    private int value;
}
