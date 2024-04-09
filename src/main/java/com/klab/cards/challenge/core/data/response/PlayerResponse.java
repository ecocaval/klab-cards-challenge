package com.klab.cards.challenge.core.data.response;

import com.klab.cards.challenge.presentation.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlayerResponse {

    private UUID id;

    private String name;

    public static PlayerResponse fromPlayer(Player player) {
        return PlayerResponse.builder()
                .id(player.getId())
                .name(player.getName())
                .build();
    }
}
