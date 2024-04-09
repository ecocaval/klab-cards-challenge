package com.klab.cards.challenge.core.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DeckOfCardsApiDeckResponse {

    private Boolean success;

    @JsonProperty("deck_id")
    private String deckId;

    private Boolean shuffled;

    private Integer remaining;
}
