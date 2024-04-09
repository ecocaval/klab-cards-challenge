package com.klab.cards.challenge.presentation.controller;

import com.klab.cards.challenge.core.data.response.GameResponse;
import com.klab.cards.challenge.presentation.usecase.GameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/game")
public class GameController {

    private final GameUseCase gameUseCase;

    @Autowired
    public GameController(GameUseCase gameUseCase) {
        this.gameUseCase = gameUseCase;
    }

    @PostMapping("/new")
    public ResponseEntity<GameResponse> createGame() {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                GameResponse.fromGame(this.gameUseCase.create())
        );
    }
}
