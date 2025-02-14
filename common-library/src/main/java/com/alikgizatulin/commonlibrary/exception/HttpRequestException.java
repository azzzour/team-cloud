package com.alikgizatulin.commonlibrary.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Getter
public class HttpRequestException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String messageKey;
    private final Object[] messageArgs;
    private final Instant timestamp;

    public HttpRequestException(String message, HttpStatus httpStatus, String messageKey) {
        super(message);
        this.httpStatus = httpStatus;
        this.messageKey = messageKey;
        this.messageArgs = null;
        this.timestamp = Instant.now();
    }

    public HttpRequestException(HttpStatus httpStatus, String messageKey, Object... messageArgs) {
        super();
        this.httpStatus = httpStatus;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
        this.timestamp = Instant.now();
    }
}
