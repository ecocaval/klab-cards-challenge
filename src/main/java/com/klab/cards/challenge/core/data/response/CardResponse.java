package com.klab.cards.challenge.core.data.response;

import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CardResponse {

    private UUID id;

    private String rank;

    private int value;

    public static CardResponse fromCard(Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .rank(card.getRank())
                .value(card.getValue())
                .build();
    }
}
