package com.klab.cards.challenge.core.data.response;

import com.klab.cards.challenge.core.data.dto.WinnerDto;
import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Hand;
import com.klab.cards.challenge.presentation.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GameResponse {

    private UUID id;

    private LocalDateTime creationDate;

    private List<WinnerDto> winners;

    private List<HandResponse> hands;

    public static GameResponse fromGame(Game game) {

        Map<Player, Integer> playerScores = game.getHands() != null ?
                game.getHands().stream().collect(Collectors.toMap(Hand::getPlayer, Hand::getScore)) :
                Collections.emptyMap();

        return GameResponse.builder()
                .id(game.getId())
                .creationDate(game.getCreationDate())
                .winners(Optional.ofNullable(game.getWinners())
                        .orElse(Collections.emptySet())
                        .stream()
                        .map(winner -> WinnerDto.fromPlayerAndScore(winner, playerScores.getOrDefault(winner, 0)))
                        .toList())
                .hands(Optional.ofNullable(game.getHands())
                        .orElse(Collections.emptySet())
                        .stream()
                        .map(HandResponse::fromHand)
                        .toList())
                .build();
    }
}
