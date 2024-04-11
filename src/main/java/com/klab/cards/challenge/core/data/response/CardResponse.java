package com.klab.cards.challenge.core.data.response;

import com.klab.cards.challenge.presentation.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CardResponse {

    private String rank;

    private int rankValue;

    public static CardResponse fromCard(Card card) {
        return CardResponse.builder()
                .rank(card.getRank())
                .rankValue(card.getRankValue())
                .build();
    }
}
