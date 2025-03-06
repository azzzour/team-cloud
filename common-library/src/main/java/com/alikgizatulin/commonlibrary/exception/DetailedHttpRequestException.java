package com.alikgizatulin.commonlibrary.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DetailedHttpRequestException extends HttpRequestException {

    private final Object details;

    public DetailedHttpRequestException(HttpStatus httpStatus, String message, String messageKey, Object details) {
        super(httpStatus, message, messageKey);
        this.details = details;
    }

    public DetailedHttpRequestException(HttpStatus httpStatus, String messageKey, Object[] messageArgs,
                                        Object details) {
        super(httpStatus, messageKey, messageArgs);
        this.details = details;
    }

    public DetailedHttpRequestException(HttpStatus httpStatus, String messageKey, Object details) {
        super(httpStatus, messageKey);
        this.details = details;
    }
}
