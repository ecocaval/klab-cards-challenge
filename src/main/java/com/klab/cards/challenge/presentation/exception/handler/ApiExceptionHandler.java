package com.klab.cards.challenge.presentation.exception.handler;

import com.klab.cards.challenge.presentation.exception.base.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConflictException.class})
    protected ResponseEntity<Object> handleConflictException(
            ConflictException e,
            WebRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return handleExceptionInternal(
                e,
                buildApiExceptionResponse(e, httpServletRequest),
                new HttpHeaders(),
                HttpStatus.CONFLICT,
                request
        );
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequestException(
            BadRequestException e,
            WebRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return handleExceptionInternal(
                e,
                buildApiExceptionResponse(e, httpServletRequest),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler({UnauthorizedException.class})
    protected ResponseEntity<Object> handleUnauthorizedException(
            UnauthorizedException e,
            WebRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return handleExceptionInternal(
                e,
                buildApiExceptionResponse(e, httpServletRequest),
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED,
                request
        );
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(
            NotFoundException e,
            WebRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return handleExceptionInternal(
                e,
                buildApiExceptionResponse(e, httpServletRequest),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }

    @ExceptionHandler({ServiceUnavailableException.class})
    protected ResponseEntity<Object> handleServiceUnavailableException(
            ServiceUnavailableException e,
            WebRequest request,
            HttpServletRequest httpServletRequest
    ) {
        return handleExceptionInternal(
                e,
                buildApiExceptionResponse(e, httpServletRequest),
                new HttpHeaders(),
                HttpStatus.SERVICE_UNAVAILABLE,
                request
        );
    }

    private ApiExceptionResponse buildApiExceptionResponse(BaseException e, HttpServletRequest httpServletRequest) {
        return ApiExceptionResponse.builder()
                .errorMessage(e.getMessage())
                .timestamp(e.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .instance(httpServletRequest.getRequestURI())
                .details(e.getDetails())
                .build();
    }
}
