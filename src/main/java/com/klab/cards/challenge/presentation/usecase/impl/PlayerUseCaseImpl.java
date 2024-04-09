package com.klab.cards.challenge.presentation.usecase.impl;

import com.klab.cards.challenge.presentation.entity.Player;
import com.klab.cards.challenge.presentation.exception.PlayerNotFoundException;
import com.klab.cards.challenge.presentation.exception.PlayersNotFoundException;
import com.klab.cards.challenge.presentation.repository.PlayerRepository;
import com.klab.cards.challenge.presentation.usecase.PlayerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
            throw new PlayersNotFoundException();
        }

        return players;
    }

    @Override
    @Cacheable(value = "playerCache", key = "#id", cacheResolver = "playerCacheResolver")
    public Player findById(String id) {
        return this.findAll()
                .stream()
                .filter(player -> player.getId().equals(UUID.fromString(id)))
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }
}
