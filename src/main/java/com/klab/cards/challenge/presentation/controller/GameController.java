package com.klab.cards.challenge.presentation.controller;

import com.klab.cards.challenge.core.data.response.GameResponse;
import com.klab.cards.challenge.core.util.UuidUtils;
import com.klab.cards.challenge.presentation.exception.InvalidGameIdException;
import com.klab.cards.challenge.presentation.usecase.GameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/games")
public class GameController {

    private final GameUseCase gameUseCase;

    @Autowired
    public GameController(GameUseCase gameUseCase) {
        this.gameUseCase = gameUseCase;
    }

    @GetMapping
    public ResponseEntity<Page<GameResponse>> findAllPageable(
            Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            this.gameUseCase.findAllPageable(pageable)
                    .map(GameResponse::fromGame)
        );
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponse> findById(
            @PathVariable("gameId") String gameId
    ) {
        if(!UuidUtils.stringIsAValidUuid(gameId)) {
            throw new InvalidGameIdException(gameId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                GameResponse.fromGame(this.gameUseCase.findById(gameId))
        );
    }

    @PostMapping
    public ResponseEntity<GameResponse> create() {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                GameResponse.fromGame(this.gameUseCase.create())
        );
    }
}
