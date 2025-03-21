package com.alikgizatulin.commonlibrary.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends HttpRequestException{
    public DuplicateException(String message, String messageKey) {
        super(HttpStatus.CONFLICT, message, messageKey);
    }

    public DuplicateException(String messageKey, Object[] messageArgs) {
        super(HttpStatus.CONFLICT, messageKey, messageArgs);
    }

    public DuplicateException (String messageKey) {
        super(HttpStatus.CONFLICT, messageKey);
    }
}
