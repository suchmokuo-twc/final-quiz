package com.twuc.shopping.exception;

import org.springframework.http.HttpStatus;

public class ProductNameExistsException extends RuntimeException implements ErrorResponseException {

    @Override
    public HttpStatus getResponseStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getErrorMessage() {
        return "商品名称已存在，请输入新的商品名称";
    }
}
