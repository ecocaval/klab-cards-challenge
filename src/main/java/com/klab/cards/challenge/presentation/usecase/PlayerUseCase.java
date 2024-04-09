package com.klab.cards.challenge.presentation.usecase;

import com.klab.cards.challenge.presentation.entity.Player;

import java.util.List;

public interface PlayerUseCase {

    List<Player> findAll();

    Player findById(String id);
}
