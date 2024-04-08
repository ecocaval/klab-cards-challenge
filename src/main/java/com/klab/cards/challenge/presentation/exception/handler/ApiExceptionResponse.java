package com.klab.cards.challenge.presentation.exception.handler;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class ApiExceptionResponse {
    private String instance;
    private String timestamp;
    private String errorMessage;
    private Object details;
}
