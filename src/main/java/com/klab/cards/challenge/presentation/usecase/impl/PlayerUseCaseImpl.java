package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.presentation.usecase.PlayerUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerUseCaseImpl implements PlayerUseCase {

    @Override
    public List<Player> findAll() {
        return null;
    }
}
