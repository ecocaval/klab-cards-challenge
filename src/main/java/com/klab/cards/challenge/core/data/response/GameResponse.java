package com.klab.cards.challenge.core.data.response;

import com.klab.cards.challenge.presentation.entity.Game;
import com.klab.cards.challenge.presentation.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GameResponse {

    private UUID id;

    private LocalDateTime creationDate;

    private LocalDateTime lastModifiedDate;

    private List<PlayerResponse> players;

    private List<PlayerResponse> winners;

    private List<HandResponse> hands;

    public static GameResponse fromGame(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .creationDate(game.getCreationDate())
                .lastModifiedDate(game.getLastModifiedDate())
                .players(game.getPlayers() != null ?
                        game.getPlayers().stream().map(PlayerResponse::fromPlayer).toList() : List.of()
                )
                .winners(game.getWinners() != null ?
                        game.getWinners().stream().map(PlayerResponse::fromPlayer).toList() : List.of()
                )
                .hands(game.getHands() != null ?
                        game.getHands().stream().map(HandResponse::fromHand).toList() : List.of()
                )
                .build();
    }
}
