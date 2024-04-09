package com.klab.cards.challenge.core.data.response;

import com.klab.cards.challenge.presentation.entity.Hand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HandResponse {

    private UUID id;

    private UUID playerId;

    private UUID gameId;

    private List<CardResponse> cards;

    public static HandResponse fromHand(Hand hand) {
        return HandResponse.builder()
                .id(hand.getId())
                .playerId(hand.getPlayer().getId())
                .gameId(hand.getGame().getId())
                .cards(hand.getCards() != null ?
                        hand.getCards().stream().map(CardResponse::fromCard).toList() : List.of()
                )
                .build();
    }
}
