package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.presentation.client.DeckOfCardsApiClient;
import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Hand;
import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.presentation.repository.GameRepository;
import com.klab.cards.challenge.presentation.usecase.CardUseCase;
import com.klab.cards.challenge.presentation.usecase.GameUseCase;
import com.klab.cards.challenge.presentation.usecase.HandUseCase;
import com.klab.cards.challenge.presentation.usecase.PlayerUseCase;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

@Service
public class GameUseCaseImpl implements GameUseCase {

    private final PlayerUseCase playerUseCase;

    private final CardUseCase cardUseCase;

    private final HandUseCase handUseCase;

    private final DeckOfCardsApiClient deckOfCardsApiClient;

    private final GameRepository gameRepository;

    @Autowired
    public GameUseCaseImpl(
            PlayerUseCase playerUseCase,
            CardUseCase cardUseCase,
            HandUseCase handUseCase,
            DeckOfCardsApiClient deckOfCardsApiClient,
            GameRepository gameRepository
    ) {
        this.playerUseCase = playerUseCase;
        this.cardUseCase = cardUseCase;
        this.handUseCase = handUseCase;
        this.deckOfCardsApiClient = deckOfCardsApiClient;
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public Game create() {

        Game newGame = initializeNewGame();

        String deckId = fetchDeckId();

        assignCardsToPlayers(newGame, deckId);

        newGame.setWinners(this.handUseCase.getWinnersFromHands(newGame.getHands()));

        return this.gameRepository.save(newGame);
    }

    private Game initializeNewGame() {

        Game newGame = new Game();

        newGame.setPlayers(new HashSet<>(this.playerUseCase.findAll().subList(0, Game.MAXIMUM_NUMBER_OF_PLAYERS)));

        return newGame;
    }

    private String fetchDeckId() {
        try {
            String deckId = this.deckOfCardsApiClient.getNewDeck().getDeckId();

            this.deckOfCardsApiClient.shuffleDeck(deckId);

            return deckId;

        } catch (FeignException exception) {
            throw new RuntimeException();
        }
    }

    private void assignCardsToPlayers(Game newGame, String deckId) {
        newGame.getPlayers().forEach(player -> processPlayerHand(player, newGame, deckId));
    }

    private void processPlayerHand(Player player, Game newGame, String deckId) {

        Hand playerHand = new Hand(player, newGame);

        List<CompletableFuture<Card>> futureCards = drawCardsForPlayer(deckId);

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureCards.toArray(new CompletableFuture[0]));

        allFutures.thenAccept(v -> {

            futureCards.forEach(futureCard -> {

                try {
                    Card card = futureCard.get();

                    playerHand.getCards().add(card);

                    playerHand.incrementScore(card.getValue());

                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });

            newGame.getHands().add(playerHand);

        }).join();
    }

    private List<CompletableFuture<Card>> drawCardsForPlayer(String deckId) {

        return IntStream.range(0, Game.MAXIMUM_NUMBER_OF_CARD_PER_PLAYER)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return this.cardUseCase.findByDeckOfCardsRank(
                                this.deckOfCardsApiClient.drawACardFromTheDeck(deckId).getDeckOfCardsRank()
                        );
                    } catch (FeignException exception) {
                        throw new RuntimeException();
                    }
                })).toList();
    }
}
