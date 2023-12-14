package com.oskarwiedeweg.cloudwork.error;

import com.oskarwiedeweg.cloudwork.error.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDto> handleError(Throwable throwable) {
        if (!(throwable instanceof ErrorResponse errorResponse)) {
            log.error("An error occurred! ",throwable);
            return construct(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
        return construct(errorResponse.getStatusCode(),
                HttpStatus.resolve(errorResponse.getStatusCode().value()).getReasonPhrase());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDto> handleError(ResponseStatusException throwable) {
        return construct(throwable.getStatusCode(), throwable.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleError(AccessDeniedException throwable) {
        return construct(HttpStatus.FORBIDDEN, throwable.getMessage());
    }


    private ResponseEntity<ErrorDto> construct(HttpStatusCode statusCode, Object error) {
        return ResponseEntity.status(statusCode).body(new ErrorDto(
                statusCode.value(),
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .build()
                        .getPath(),
                error,
                LocalDateTime.now()
        ));
    }


}
