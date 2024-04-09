package com.klab.cards.challenge.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    ERROR_PLAYERS_NOT_FOUND_IN_DATABASE("Nenhum jogador foi encontrado no banco de dados."),
    ERROR_CARDS_NOT_FOUND_IN_DATABASE("Nenhuma carta foi encontrado no banco de dados."),
    ERROR_CARD_NOT_FOUND_BY_DECK_OF_CARD_RANK("Nenhuma carta com este rank foi encontrada.")
    ;

    private final String message;
}
