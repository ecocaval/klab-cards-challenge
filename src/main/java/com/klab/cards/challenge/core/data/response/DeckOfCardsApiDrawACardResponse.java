package com.klab.cards.challenge.core.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DeckOfCardsApiDrawACardResponse {

    private List<DeckOfCardsApiCardResponse> cards;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class DeckOfCardsApiCardResponse {

        @JsonProperty("value")
        private String deckOfCardsRank;
    }

    public String getDeckOfCardsRank() {

        if(this.cards.isEmpty()) {
            return null;
        }

        return this.cards.get(0).getDeckOfCardsRank();
    }
}
