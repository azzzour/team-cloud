package com.alikgizatulin.commonlibrary.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpRequestException {

    public NotFoundException(String message, String messageKey) {
        super(HttpStatus.NOT_FOUND, message, messageKey);
    }

    public NotFoundException(String messageKey, Object[] messageArgs) {
        super(HttpStatus.NOT_FOUND, messageKey, messageArgs);
    }

    public NotFoundException(String messageKey) {
        super(HttpStatus.NOT_FOUND, messageKey);
    }
}
