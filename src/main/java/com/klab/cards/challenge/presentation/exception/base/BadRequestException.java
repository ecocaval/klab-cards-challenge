package com.klab.cards.challenge.presentation.exception.base;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Object details) {
        super(message, details);
    }
}
