package com.twuc.shopping.exception;

import org.springframework.http.HttpStatus;

public interface ErrorResponseException {

    HttpStatus getResponseStatus();

    String getErrorMessage();
}
