package com.klab.cards.challenge.core.data.response;

import com.klab.cards.challenge.presentation.entity.Hand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class HandResponse {

    private PlayerResponse player;

    private List<CardResponse> cards;

    private int score;

    public static HandResponse fromHand(Hand hand) {
        return HandResponse.builder()
                .player(PlayerResponse.fromPlayer(hand.getPlayer()))
                .cards(hand.getCards() != null ?
                        hand.getCards().stream().map(CardResponse::fromCard).toList() : List.of()
                )
                .score(hand.getScore())
                .build();
    }
}
