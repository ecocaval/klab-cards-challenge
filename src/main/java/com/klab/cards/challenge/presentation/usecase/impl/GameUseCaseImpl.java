package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.presentation.client.DeckOfCardsApiClient;
import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Hand;
import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.presentation.exception.DeckOfCardsApiCommunicationException;
import com.klab.cards.challenge.presentation.exception.GameNotFoundException;
import com.klab.cards.challenge.presentation.repository.GameRepository;
import com.klab.cards.challenge.presentation.usecase.CardUseCase;
import com.klab.cards.challenge.presentation.usecase.GameUseCase;
import com.klab.cards.challenge.presentation.usecase.HandUseCase;
import com.klab.cards.challenge.presentation.usecase.PlayerUseCase;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
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
    public Page<Game> findAllPageable(Pageable pageable) {
        return this.gameRepository.findAllByOrderByCreationDateDesc(pageable);
    }

    @Override
    public Page<Game> findAllGamesWonByPlayerPageable(Player player, Pageable pageable) {
        return this.gameRepository.findAllByWinnersContainingOrderByCreationDateDesc(player, pageable);
    }

    @Override
    public Game findById(String gameId) {
        return this.gameRepository.findById(UUID.fromString(gameId))
                .orElseThrow(() -> new GameNotFoundException(gameId));
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

        newGame.setPlayers(new HashSet<>(this.playerUseCase.findAll()));

        return newGame;
    }

    private String fetchDeckId() {
        try {
            String deckId = this.deckOfCardsApiClient.getNewDeck().getDeckId();

            this.deckOfCardsApiClient.shuffleDeck(deckId);

            return deckId;

        } catch (FeignException exception) {
            throw new DeckOfCardsApiCommunicationException();
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

                    playerHand.incrementScore(card.getRankValue());

                } catch (InterruptedException | ExecutionException e) {
                    throw new DeckOfCardsApiCommunicationException();
                }
            });

            newGame.getHands().add(playerHand);

        }).join();
    }

    private List<CompletableFuture<Card>> drawCardsForPlayer(String deckId) {

        return IntStream.range(0, Game.NUMBER_OF_CARDS_PER_PLAYER)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return this.cardUseCase.findByDeckOfCardsRank(
                                this.deckOfCardsApiClient.drawACardFromTheDeck(deckId).getDeckOfCardsRank()
                        );
                    } catch (FeignException exception) {
                        throw new DeckOfCardsApiCommunicationException();
                    }
                })).toList();
    }
}
