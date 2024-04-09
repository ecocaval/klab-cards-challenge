package com.klab.cards.challenge.presentation.repository;

import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {

    Page<Game> findAllByOrderByCreationDateDesc(Pageable pageable);

    Page<Game> findAllByWinnersContainingOrderByCreationDateDesc(Player player, Pageable pageable);

}
