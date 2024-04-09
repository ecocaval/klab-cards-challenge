package com.klab.cards.challenge.presentation.controller;

import com.klab.cards.challenge.core.data.response.GameResponse;
import com.klab.cards.challenge.core.data.response.PlayerResponse;
import com.klab.cards.challenge.core.util.UuidUtils;
import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.presentation.exception.InvalidPlayerIdException;
import com.klab.cards.challenge.presentation.usecase.GameUseCase;
import com.klab.cards.challenge.presentation.usecase.PlayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/players")
public class PlayerController {

    private final PlayerUseCase playerUseCase;

    private final GameUseCase gameUseCase;

    @Autowired
    public PlayerController(PlayerUseCase playerUseCase, GameUseCase gameUseCase) {
        this.playerUseCase = playerUseCase;
        this.gameUseCase = gameUseCase;
    }

    @GetMapping
    public ResponseEntity<Set<PlayerResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
            this.playerUseCase.findAll()
                    .stream()
                    .map(PlayerResponse::fromPlayer)
                    .collect(Collectors.toSet())
        );
    }

    @GetMapping("/{playerId}/games/won")
    public ResponseEntity<Page<GameResponse>> findAllGamesWonById(
            @PathVariable("playerId") String playerId,
            Pageable pageable
    ) {
        if(!UuidUtils.stringIsAValidUuid(playerId)) {
            throw new InvalidPlayerIdException(playerId);
        }

        Player player = this.playerUseCase.findById(playerId);

        return ResponseEntity.status(HttpStatus.OK).body(
                this.gameUseCase.findAllGamesWonByPlayerPageable(player,pageable)
                        .map(GameResponse::fromGame)
        );
    }
}
