package com.klab.cards.challenge.presentation.repository;

import com.klab.cards.challenge.presentation.entity.Card;
import com.klab.cards.challenge.presentation.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    Optional<Card> findByDeckOfCardsRank(String deckOfCardsRank);

}
