package com.twuc.shopping.controller;

import com.twuc.shopping.dto.ErrorResponseDto;
import com.twuc.shopping.exception.ErrorResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHandlers {

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponseDto> handleException(Exception exception) {
        if (exception instanceof ErrorResponseException) {
            ErrorResponseException errorResponseException = (ErrorResponseException) exception;

            return ResponseEntity
                    .status(errorResponseException.getResponseStatus())
                    .body(new ErrorResponseDto(errorResponseException.getErrorMessage()));
        }

        return ResponseEntity
                .status(500)
                .body(new ErrorResponseDto(exception.getMessage()));
    }
}
