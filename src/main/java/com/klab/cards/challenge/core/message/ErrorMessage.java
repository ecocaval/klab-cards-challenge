package com.klab.cards.challenge.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    ERROR_PLAYERS_NOT_FOUND_IN_DATABASE("Nenhum jogador foi encontrado no banco de dados."),
    ERROR_CARDS_NOT_FOUND_IN_DATABASE("Nenhuma carta foi encontrado no banco de dados."),
    ERROR_INVALID_GAME_ID("O identificador do jogo informado é inválido."),
    ERROR_INVALID_PLAYER_ID("O identificador do jogador informado é inválido."),
    ERROR_GAME_NOT_FOUND_BY_ID("O jogo com este identificador nao foi encontrado."),
    ERROR_PLAYER_NOT_FOUND_BY_ID("O jogador com este identificador nao foi encontrado."),
    ERROR_CARD_NOT_FOUND_BY_DECK_OF_CARD_RANK("Nenhuma carta com este rank foi encontrada.")
    ;

    private final String message;
}
