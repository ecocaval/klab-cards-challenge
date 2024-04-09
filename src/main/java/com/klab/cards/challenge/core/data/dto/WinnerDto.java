package com.klab.cards.challenge.core.data.dto;

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
public class WinnerDto {

    private UUID id;

    private String name;

    private int score;

    public static WinnerDto fromPlayerAndScore(Player player, int score) {
        return WinnerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .score(score)
                .build();
    }
}
