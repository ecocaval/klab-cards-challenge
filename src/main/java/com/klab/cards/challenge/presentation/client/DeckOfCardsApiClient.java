package com.klab.cards.challenge.presentation.client;

import com.klab.cards.challenge.core.data.response.DeckOfCardsApiDeckResponse;
import com.klab.cards.challenge.core.data.response.DeckOfCardsApiDrawACardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cep", url = "https://deckofcardsapi.com/api")
public interface DeckOfCardsApiClient {

    @GetMapping(value = "/deck/new?jokers_enabled=false", consumes = "application/json")
    DeckOfCardsApiDeckResponse getNewDeck();

    @GetMapping(value = "/deck/{deck_id}/shuffle/?remaining=true", consumes = "application/json")
    DeckOfCardsApiDeckResponse shuffleDeck(@RequestParam("deck_id") String deckId);

    @GetMapping(value = "/deck/{deck_id}/draw", consumes = "application/json")
    DeckOfCardsApiDrawACardResponse drawACardFromTheDeck(@RequestParam("deck_id") String deckId);
}
