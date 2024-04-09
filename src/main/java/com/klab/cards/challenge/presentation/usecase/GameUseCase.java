package com.klab.cards.challenge.presentation.usecase;

import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GameUseCase {

    Page<Game> findAllPageable(Pageable pageable);

    Page<Game> findAllGamesWonByPlayerPageable(Player player, Pageable pageable);

    Game findById(String gameId);

    Game create();
}
