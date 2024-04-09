package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.core.message.ErrorMessage;
import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.presentation.exception.base.NotFoundException;
import com.klab.cards.challenge.presentation.repository.PlayerRepository;
import com.klab.cards.challenge.presentation.usecase.PlayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerUseCaseImpl implements PlayerUseCase {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerUseCaseImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    @Cacheable(value = "playersCache", cacheResolver = "playersCacheResolver")
    public List<Player> findAll() {

        List<Player> players = this.playerRepository.findAll();

        if(players.isEmpty()) {
            throw new NotFoundException(ErrorMessage.ERROR_PLAYERS_NOT_FOUND_IN_DATABASE.getMessage());
        }

        return players;
    }
}
