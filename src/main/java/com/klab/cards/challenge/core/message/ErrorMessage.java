package com.klab.cards.challenge.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    ERROR_XXXXX("XXXX.");

    private final String message;
}
