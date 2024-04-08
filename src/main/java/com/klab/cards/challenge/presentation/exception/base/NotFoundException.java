package com.klab.cards.challenge.presentation.exception.base;

public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Object details) {
        super(message, details);
    }
}
